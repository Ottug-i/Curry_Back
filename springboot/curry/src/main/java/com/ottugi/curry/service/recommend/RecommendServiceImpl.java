package com.ottugi.curry.service.recommend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ottugi.curry.config.GlobalConfig;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.except.BaseCode;
import com.ottugi.curry.except.NotFoundException;
import com.ottugi.curry.service.PageUtil;
import com.ottugi.curry.service.recipe.RecipeService;
import com.ottugi.curry.service.user.UserService;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeRequestDto;
import com.ottugi.curry.web.dto.recommend.RecipeIngListResponseDto;
import com.ottugi.curry.web.dto.recommend.RecommendRequestDto;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserService userService;
    private final RecipeService recipeService;
    private final GlobalConfig globalConfig;

    private String FLASK_API_URL;

    @PostConstruct
    private void setFlaskApiUrl() {
        String flask_host = globalConfig.getFlask_host();
        int flask_port = globalConfig.getFlask_port();
        this.FLASK_API_URL = "http://" + flask_host + ":" + flask_port;
    }

    @Override
    public Page<RecipeIngListResponseDto> findRecipePageByIngredientsDetection(RecipeRequestDto requestDto) {
        User user = userService.findUserByUserId(requestDto.getUserId());
        List<Recipe> recipeList = recipeService.findByRecipeListByIngredientsContaining(requestDto.getIngredients().get(0));
        List<Recipe> recipeDetectionList = recipeList.stream()
                .filter(recipeService.filterPredicateForOptions(requestDto.getTime(), requestDto.getDifficulty(), requestDto.getComposition()))
                .collect(Collectors.toList());
        List<RecipeIngListResponseDto> pagedRecipeList = sortedRecipeList(user, requestDto, recipeDetectionList).stream()
                .map(recipeMap -> new RecipeIngListResponseDto(requestDto.getIngredients(), recipeMap.keySet().iterator().next(), user))
                .collect(Collectors.toList());
        return PageUtil.convertResponseDtoPages(pagedRecipeList, requestDto.getPage(), requestDto.getSize());
    }

    @Override
    public List<Long> findRecipeIdListByBookmarkRecommend(Long recipeId, int page) {
        try {
            String apiUrl = String.format("%s/bookmark/recommend?recipe_id=%d&page=%d", FLASK_API_URL, recipeId, page);
            String response = restTemplate.getForObject(apiUrl, String.class);
            if (response == null) {
                return Collections.emptyList();
            }
            return Arrays.stream(objectMapper.readValue(response, Long[].class)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new NotFoundException(BaseCode.RECOMMEND_NOT_FOUND);
        }
    }

    @Override
    public List<Long> findRecipeIdListByRatingRecommend(Long userId, int page, Long[] bookmarkList) {
        try {
            String apiUrl = String.format("%s/rating/recommend?user_id=%d&page=%d", FLASK_API_URL, userId, page);
            if (bookmarkList != null) {
                apiUrl += addBookmarkParams(bookmarkList);
            }
            String response = restTemplate.getForObject(apiUrl, String.class);
            Object[] resultList = objectMapper.readValue(response, Object[].class);
            updateUserFavoriteGenre(resultList[0], userId);
            return Arrays.stream(objectMapper.convertValue(resultList[1], Long[].class)).collect(Collectors.toList());
        } catch (RestClientException | JsonProcessingException e) {
            throw new NotFoundException(BaseCode.RECOMMEND_NOT_FOUND);
        }
    }

    @Override
    public List<RecipeListResponseDto> findBookmarkOrRatingRecommendList(RecommendRequestDto recommendRequestDto) {
        User user = userService.findUserByUserId(recommendRequestDto.getUserId());
        List<Recipe> recommendRecipeList = recipeService.findRecipeListByRecipeIdIn(recommendRequestDto.getRecipeId());
        return recommendRecipeList.stream()
                .map(recipe -> new RecipeListResponseDto(recipe, user))
                .collect(Collectors.toList());
    }

    private String addBookmarkParams(Long[] bookmarkList) {
        StringBuilder bookmarkParams = new StringBuilder();
        for (Long bookmarkId : bookmarkList) {
            bookmarkParams.append("&bookmark_list=").append(bookmarkId);
        }
        return bookmarkParams.toString();
    }

    private void updateUserFavoriteGenre(Object genre, Long userId) {
        User user = userService.findUserByUserId(userId);
        user.updateGenre((String) genre);
    }

    private List<Map<Recipe, Integer>> sortedRecipeList(User user, RecipeRequestDto recipeRequestDto, List<Recipe> recipeSearchList) {
        return recipeSearchList.stream()
                .filter(recipe -> isAllIngredientsIncluded(recipe, recipeRequestDto.getIngredients()))
                .map(recipe -> containFavoriteGenre(recipe, user))
                .sorted((recipe1, recipe2) -> recipe2.values().iterator().next().compareTo(recipe1.values().iterator().next()))
                .collect(Collectors.toList());
    }

    private boolean isAllIngredientsIncluded(Recipe recipe, List<String> ingredients) {
        return ingredients.stream().skip(1).allMatch(recipe.getIngredients()::contains);
    }

    private Map<Recipe, Integer> containFavoriteGenre(Recipe recipe, User user) {
        Map<Recipe, Integer> recipeMap = new HashMap<>();
        String favoriteGenre = user.getFavoriteGenre() != null ? user.getFavoriteGenre() : "";
        int genreMatch = recipe.getGenre().contains(favoriteGenre) ? 1 : 0;
        recipeMap.put(recipe, genreMatch);
        return recipeMap;
    }
}