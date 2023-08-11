package com.ottugi.curry.service.recommend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.ottugi.curry.domain.bookmark.BookmarkRepository;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.web.dto.recommend.RecipeIngListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeRequestDto;
import com.ottugi.curry.web.dto.recommend.RatingRequestDto;
import com.ottugi.curry.web.dto.recommend.RatingResponseDto;
import com.ottugi.curry.web.dto.recommend.RecommendListResponseDto;
import com.ottugi.curry.web.dto.recommend.RecommendRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService {

    private final String FLASK_API_URL = "http://localhost:5000";
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final BookmarkRepository bookmarkRepository;

    // 랜덤 레시피 리스트 조회
    @Override
    public List<RecommendListResponseDto> getRandomRecipe() {

        long min = 1L;
        long max = 3616L;
        int batchSize = 10;

        List<Long> selectedIdList = ThreadLocalRandom.current().longs(min, max + 1).distinct().limit(batchSize).boxed().collect(Collectors.toList());
        List<Recipe> recipeList = recipeRepository.findByIdIn(selectedIdList);

        return recipeList.stream().map(recipe -> new RecommendListResponseDto(recipe)).collect(Collectors.toList());
    }

    // 유저 레시피 평점 조회
    @Override
    public RatingResponseDto getUserRating(Long userId, Long recipeId) throws JsonProcessingException {

        String apiUrl = String.format("%s/rating/user_ratings?user_id=%d&recipe_id=%d", FLASK_API_URL, userId, recipeId);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        String response = restTemplate.getForObject(apiUrl, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Double[] result = objectMapper.readValue(response, Double[].class);
            return new RatingResponseDto(Arrays.asList(result));
        } catch (MismatchedInputException e) {
            return null;
        }
    }

    // 유저 레시피 평점 추가 또는 수정
    @Override
    public Boolean updateUserRating(RatingRequestDto ratingRequestDto) {

        String apiUrl = String.format("%s/rating/user_ratings", FLASK_API_URL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RatingRequestDto> httpEntity = new HttpEntity<>(ratingRequestDto, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, httpEntity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return true;
        } else {
            return false;
        }
    }

    // 재료 추천 레시피 목록 조회
    @Override
    public Page<RecipeIngListResponseDto> getIngredientsRecommendList(RecipeRequestDto recipeRequestDto) {

        User user =  userRepository.findById(recipeRequestDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));

        List<Recipe> recipeList = recipeRepository.findByIngredientsContaining(recipeRequestDto.getIngredients().get(0));
        List<Map<Recipe, Integer>> sortedRecipeList = new ArrayList<>();
        for (Recipe recipe: recipeList) {
            boolean isAllIncluded = true;
            for (int i = 1; i < recipeRequestDto.getIngredients().size(); i++) {
                if (!recipe.getIngredients().contains(recipeRequestDto.getIngredients().get(i))) {
                    isAllIncluded = false;
                    break;
                }
            }
            if (isAllIncluded) {
                Map<Recipe, Integer> recipeMap = new HashMap<>();
                if (recipe.getGenre().contains(user.getFavoriteGenre())) {
                    recipeMap.put(recipe, 1);
                }
                else {
                    recipeMap.put(recipe, 0);
                }
                sortedRecipeList.add(recipeMap);
            }
        }

        sortedRecipeList.sort((recipeMap1, recipeMap2) -> recipeMap2.values().iterator().next().compareTo(recipeMap1.values().iterator().next()));

        int totalItems = sortedRecipeList.size();
        int fromIndex = Math.max(0, (recipeRequestDto.getPage() - 1) * recipeRequestDto.getSize());
        int toIndex = Math.min(totalItems, fromIndex + recipeRequestDto.getSize());

        List<RecipeIngListResponseDto> pagedRecipeList = sortedRecipeList.subList(fromIndex, toIndex)
                .stream()
                .map(recipeMap -> new RecipeIngListResponseDto(recipeRequestDto.getIngredients(), recipeMap.keySet().iterator().next(), isBookmarked(recipeRequestDto.getUserId(), recipeMap.keySet().iterator().next().getRecipeId())))
                .collect(Collectors.toList());

        return new PageImpl<>(pagedRecipeList, PageRequest.of(recipeRequestDto.getPage() - 1, recipeRequestDto.getSize()), totalItems);
    }

    // 북마크 추천 레시피 아이디 목록 조회
    @Override
    public List<Long> getRecommendBookmarkId(Long recipeId, int page) throws JsonProcessingException {

        String apiUrl = String.format("%s/bookmark/recommend?recipe_id=%d&page=%d", FLASK_API_URL, recipeId, page);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        String response = restTemplate.getForObject(apiUrl, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        Long[] result = objectMapper.readValue(response, Long[].class);

        if (response != null) {
            return Arrays.asList(result);
        } else {
            return Collections.emptyList();
        }
    }

    // 평점 추천 레시피 아이디 목록 조회
    @Override
    public List<Long> getRecommendRatingId(Long userId, int page, Long[] bookmarkList) throws JsonProcessingException {

        String apiUrl = String.format("%s/rating/recommend?user_id=%d&page=%d", FLASK_API_URL, userId, page);
        for (Long bookmarkId : bookmarkList) {
            apiUrl += "&bookmark_list=" + bookmarkId;
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        String response = restTemplate.getForObject(apiUrl, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        Object[] resultList = objectMapper.readValue(response, Object[].class);

        String genre = (String) resultList[0];
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
        user.updateGenre(genre);

        Long[] result = objectMapper.convertValue(resultList[1], Long[].class);

        if (response != null) {
            return Arrays.asList(result);
        } else {
            return Collections.emptyList();
        }
    }

    // 평점/북마크 추천 레시피 목록 조회
    @Override
    public List<RecipeListResponseDto> getBookmarkOrRatingRecommendList(RecommendRequestDto recommendRequestDto) {

        List<Recipe> recipeList = recipeRepository.findByRecipeIdIn(recommendRequestDto.getRecipeId());

        Map<Long, Recipe> recipeMap = recipeList.stream().collect(Collectors.toMap(Recipe::getRecipeId, Function.identity()));
        List<Recipe> sortedRecipeList = recommendRequestDto.getRecipeId().stream()
                .map(recipeMap::get)
                .collect(Collectors.toList());

        if (recipeList.size() != recommendRequestDto.getRecipeId().size()) {
            throw new IllegalArgumentException("해당 레시피가 없습니다.");
        }

        return sortedRecipeList.stream().map(recipe -> new RecipeListResponseDto(recipe, isBookmarked(recommendRequestDto.getUserId(), recipe.getRecipeId()))).collect(Collectors.toList());
    }

    // 북마크 여부
    public Boolean isBookmarked(Long userId, Long recipeId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
        Recipe recipe = recipeRepository.findByRecipeId(recipeId).orElse(null);
        return bookmarkRepository.findByUserIdAndRecipeId(user, recipe) != null;
    }
}