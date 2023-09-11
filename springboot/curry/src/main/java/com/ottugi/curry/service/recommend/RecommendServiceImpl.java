package com.ottugi.curry.service.recommend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ottugi.curry.config.GlobalConfig;
import com.ottugi.curry.except.BaseCode;
import com.ottugi.curry.except.BaseException;
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
public class RecommendServiceImpl implements RecommendService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CommonService commonService;

    private final String flask_host;
    private final int flask_port;

    private final String FLASK_API_URL;

    public RecommendServiceImpl(RestTemplate restTemplate, CommonService commonService, GlobalConfig config) {
        this.restTemplate = restTemplate;
        this.commonService = commonService;
        this.flask_host = config.getFlask_host();
        this.flask_port = config.getFlask_port();
        this.FLASK_API_URL = "http://" + flask_host + ":" + flask_port;
    }

    // 랜덤 레시피 리스트 조회
    @Override
    @Transactional(readOnly = true)
    public List<RecommendListResponseDto> getRandomRecipe() {
        long min = 1L;
        long max = 3616L;
        int batchSize = 10;

        List<Long> selectedIdList = ThreadLocalRandom.current().longs(min, max + 1).distinct().limit(batchSize).boxed().collect(Collectors.toList());
        List<Recipe> recipeList = commonService.findByIdIn(selectedIdList);

        return recipeList.stream().map(recipe -> new RecommendListResponseDto(recipe)).collect(Collectors.toList());
    }

    // 유저 레시피 평점 조회
    @Override
    @Transactional
    public RatingResponseDto getUserRating(Long userId, Long recipeId) {
        try {
            String apiUrl = String.format("%s/rating/user_ratings?user_id=%d&recipe_id=%d", FLASK_API_URL, userId, recipeId);
            System.out.println(FLASK_API_URL);

            String response = restTemplate.getForObject(apiUrl, String.class);
            
            if (response.equals("false")) {
                throw new BaseException(BaseCode.RECOMMEND_NOT_FOUND);
            }
            else {
                Double[] result = objectMapper.readValue(response, Double[].class);
                return new RatingResponseDto(Arrays.asList(result));
            }
        } catch (NullPointerException e) {
            return null;
        }
        catch (RestClientException | JsonProcessingException e) {
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

            if (Objects.equals(responseEntity.getBody(), "true")) {
                return true;
            }
            else {
                throw new BaseException(BaseCode.RECOMMEND_NOT_FOUND);
            }
        } catch (RestClientException e) {
            throw new BaseException(BaseCode.RECOMMEND_NOT_FOUND);
        }
    }

    // 유저 레시피 평점 삭제
    @Override
    @Transactional
    public Boolean deleteUserRating(Long userId, Long recipeId) {
        try {
            String apiUrl = String.format("%s/rating/user_ratings?user_id=%d&recipe_id=%d", FLASK_API_URL, userId, recipeId);

            restTemplate.delete(apiUrl);

            return true;
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
        List<Recipe> recipeSearchList = recipeList.stream().filter(recipe -> isRecipeMatching(recipe, recipeRequestDto)).collect(Collectors.toList());

        List<RecipeIngListResponseDto> pagedRecipeList = sortedRecipeList(user, recipeRequestDto, recipeSearchList)
                .stream()
                .map(recipeMap -> new RecipeIngListResponseDto(recipeRequestDto.getIngredients(),
                        recipeMap.keySet().iterator().next(), commonService.isBookmarked(user, recipeMap.keySet().iterator().next())))
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
            if(bookmarkList != null) {
                for (Long bookmarkId : bookmarkList) {
                    apiUrl += "&bookmark_list=" + bookmarkId;
                }
            }

            String response = restTemplate.getForObject(apiUrl, String.class);

            Object[] resultList = objectMapper.readValue(response, Object[].class);

            String genre = (String) resultList[0];
            if (genre != null) {
                User user = commonService.findByUserId(userId);
                user.updateGenre(genre);
            }

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

    // 재료 추천 레시피 옵션 검색
    @Transactional(readOnly = true)
    private boolean isRecipeMatching(Recipe recipe, RecipeRequestDto recipeRequestDto) {
        if (recipeRequestDto.getTime().isBlank() && recipeRequestDto.getDifficulty().isBlank() && recipeRequestDto.getComposition().isBlank()) {
            return true;
        }
        return commonService.isRecipeMatching(recipe, recipeRequestDto.getTime(), recipeRequestDto.getDifficulty(), recipeRequestDto.getComposition());
    }

    // 재료 추천 레시피 장르에 따라 정렬
    @Transactional(readOnly = true)
    private List<Map<Recipe, Integer>> sortedRecipeList(User user, RecipeRequestDto recipeRequestDto, List<Recipe> recipeSearchList) {
        return recipeSearchList.stream()
                .filter(recipe -> isAllIngredientsIncluded(recipe, recipeRequestDto.getIngredients()))
                .map(recipe -> {
                    Map<Recipe, Integer> recipeMap = new HashMap<>();
                    String favoriteGenre = user.getFavoriteGenre() != null ? user.getFavoriteGenre() : "";
                    int genreMatch = recipe.getGenre().contains(favoriteGenre) ? 1 : 0;
                    recipeMap.put(recipe, genreMatch);
                    return recipeMap;
                })
                .sorted((recipeMap1, recipeMap2) -> recipeMap2.values().iterator().next().compareTo(recipeMap1.values().iterator().next()))
                .collect(Collectors.toList());
    }

    // 재료 추천 레시피 목록 조회
    @Transactional(readOnly = true)
    private boolean isAllIngredientsIncluded(Recipe recipe, List<String> ingredients) {
        for (int i = 1; i < ingredients.size(); i++) {
            if (!recipe.getIngredients().contains(ingredients.get(i))) {
                return false;
            }
        }
        return true;
    }
}