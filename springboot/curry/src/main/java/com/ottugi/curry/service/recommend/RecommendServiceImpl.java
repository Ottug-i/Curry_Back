package com.ottugi.curry.service.recommend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ottugi.curry.config.exception.BaseCode;
import com.ottugi.curry.config.exception.BaseException;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.service.CommonService;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService {

    private final String FLASK_API_URL = "http://localhost:5000";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CommonService commonService;

    // 랜덤 레시피 리스트 조회
    @Override
    @Transactional(readOnly = true)
    public List<RecommendListResponseDto> getRandomRecipe() {
        long min = 1L;
        long max = 3616L;
        int batchSize = 10;

        List<Long> selectedIdList = ThreadLocalRandom.current().longs(min, max + 1).distinct().limit(batchSize).boxed().collect(Collectors.toList());
        System.out.println(selectedIdList);
        List<Recipe> recipeList = commonService.findByIdIn(selectedIdList);

        return recipeList.stream().map(recipe -> new RecommendListResponseDto(recipe)).collect(Collectors.toList());
    }

    // 유저 레시피 평점 조회
    @Override
    @Transactional
    public RatingResponseDto getUserRating(Long userId, Long recipeId) throws JsonProcessingException {
        try {
            String apiUrl = String.format("%s/rating/user_ratings?user_id=%d&recipe_id=%d", FLASK_API_URL, userId, recipeId);

            String response = restTemplate.getForObject(apiUrl, String.class);

            Double[] result = objectMapper.readValue(response, Double[].class);
            return new RatingResponseDto(Arrays.asList(result));
        } catch (RestClientException | JsonProcessingException e) {
            throw new BaseException(BaseCode.RECOMMEND_NOT_FOUND);
        }

    }

    // 유저 레시피 평점 추가 또는 수정
    @Override
    @Transactional
    public Boolean updateUserRating(RatingRequestDto ratingRequestDto) {
        try {
            String apiUrl = String.format("%s/rating/user_ratings", FLASK_API_URL);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<RatingRequestDto> httpEntity = new HttpEntity<>(ratingRequestDto, headers);

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, httpEntity, String.class);

            return responseEntity.getStatusCode().is2xxSuccessful();
        } catch (RestClientException e) {
            throw new BaseException(BaseCode.RECOMMEND_NOT_FOUND);
        }
    }

    // 재료 추천 레시피 목록 조회
    @Override
    @Transactional(readOnly = true)
    public Page<RecipeIngListResponseDto> getIngredientsRecommendList(RecipeRequestDto recipeRequestDto) {
        User user =  commonService.findByUserId(recipeRequestDto.getUserId());

        List<Recipe> recipeList = commonService.findByIngredientsContaining(recipeRequestDto.getIngredients().get(0));
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

        List<RecipeIngListResponseDto> pagedRecipeList = sortedRecipeList.stream()
                .map(recipeMap -> new RecipeIngListResponseDto(recipeRequestDto.getIngredients(), recipeMap.keySet().iterator().next(), commonService.isBookmarked(user, recipeMap.keySet().iterator().next())))
                .collect(Collectors.toList());

        return commonService.getPage(pagedRecipeList, recipeRequestDto.getPage(), recipeRequestDto.getSize());
    }

    // 북마크 추천 레시피 아이디 목록 조회
    @Override
    @Transactional
    public List<Long> getRecommendBookmarkId(Long recipeId, int page) throws JsonProcessingException {
        try {
            String apiUrl = String.format("%s/bookmark/recommend?recipe_id=%d&page=%d", FLASK_API_URL, recipeId, page);

            String response = restTemplate.getForObject(apiUrl, String.class);

            Long[] result = objectMapper.readValue(response, Long[].class);

            if (response != null) {
                return Arrays.asList(result);
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e) {
            throw new BaseException(BaseCode.RECOMMEND_NOT_FOUND);
        }
    }

    // 평점 추천 레시피 아이디 목록 조회
    @Override
    @Transactional
    public List<Long> getRecommendRatingId(Long userId, int page, Long[] bookmarkList) throws JsonProcessingException {
        try {
            String apiUrl = String.format("%s/rating/recommend?user_id=%d&page=%d", FLASK_API_URL, userId, page);
            for (Long bookmarkId : bookmarkList) {
                apiUrl += "&bookmark_list=" + bookmarkId;
            }

            String response = restTemplate.getForObject(apiUrl, String.class);

            Object[] resultList = objectMapper.readValue(response, Object[].class);

            String genre = (String) resultList[0];
            User user = commonService.findByUserId(userId);
            user.updateGenre(genre);

            Long[] result = objectMapper.convertValue(resultList[1], Long[].class);
            return Arrays.asList(result);
        } catch (RestClientException | JsonProcessingException e) {
            throw new BaseException(BaseCode.RECOMMEND_NOT_FOUND);
        }
    }

    // 평점/북마크 추천 레시피 목록 조회
    @Override
    @Transactional(readOnly = true)
    public List<RecipeListResponseDto> getBookmarkOrRatingRecommendList(RecommendRequestDto recommendRequestDto) {
        User user = commonService.findByUserId(recommendRequestDto.getUserId());

        List<Recipe> recipeList = commonService.findByRecipeIdIn(recommendRequestDto.getRecipeId());
        Map<Long, Recipe> recipeMap = recipeList.stream().collect(Collectors.toMap(Recipe::getRecipeId, Function.identity()));
        List<Recipe> sortedRecipeList = recommendRequestDto.getRecipeId().stream()
                .map(recipeMap::get)
                .collect(Collectors.toList());

        if (recipeList.size() != recommendRequestDto.getRecipeId().size()) {
            throw new IllegalArgumentException("해당 레시피가 없습니다.");
        }

        return sortedRecipeList.stream().map(recipe -> new RecipeListResponseDto(recipe, commonService.isBookmarked(user, recipe))).collect(Collectors.toList());
    }
}