package com.ottugi.curry.service.recommend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ottugi.curry.config.GlobalConfig;
import com.ottugi.curry.domain.recipe.Genre;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.service.recipe.RecipeService;
import com.ottugi.curry.service.user.UserService;
import com.ottugi.curry.util.PageConverter;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recommend.IngredientDetectionRecipeListResponseDto;
import com.ottugi.curry.web.dto.recommend.IngredientDetectionRecipeRequestDto;
import com.ottugi.curry.web.dto.recommend.RecommendRecipeRequestDto;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserService userService;
    private final RecipeService recipeService;
    private final GlobalConfig globalConfig;

    private String flaskApiUrl;

    @PostConstruct
    private void setFlaskApiUrl() {
        this.flaskApiUrl = String.format("http://%s:%d", globalConfig.getFlaskHost(), globalConfig.getFlaskPort());
    }

    @Override
    public Page<IngredientDetectionRecipeListResponseDto> findRecipePageByIngredientsDetection(IngredientDetectionRecipeRequestDto requestDto) {
        User user = userService.findUserByUserId(requestDto.getUserId());
        List<Recipe> detectedRecipeList = findRecipeListContainingIngredients(requestDto);
        List<IngredientDetectionRecipeListResponseDto> sortedRecipeList = sortRecipeListByPreference(user, requestDto, detectedRecipeList);
        return PageConverter.convertListToPage(sortedRecipeList, requestDto.getPage(), requestDto.getSize());
    }

    @Override
    public List<Long> findRecipeIdListByBookmarkRecommend(Long recipeId, int page) throws JsonProcessingException {
        String apiUrl = String.format("%s/bookmark/recommend?recipe_id=%d&page=%d", flaskApiUrl, recipeId, page);
        String response = restTemplate.getForObject(apiUrl, String.class);
        if (response == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(objectMapper.readValue(response, Long[].class)).collect(Collectors.toList());
    }

    @Override
    public List<Long> findRecipeIdListByRatingRecommend(Long userId, int page, Long[] bookmarkList) throws JsonProcessingException {
        String apiUrl = String.format("%s/rating/recommend?user_id=%d&page=%d%s", flaskApiUrl, userId, page, addBookmarkParams(bookmarkList));
        String response = restTemplate.getForObject(apiUrl, String.class);
        Object[] resultList = objectMapper.readValue(response, Object[].class);
        updateUserFavoriteGenre((String) resultList[0], userId);
        return Arrays.stream(objectMapper.convertValue(resultList[1], Long[].class)).collect(Collectors.toList());
    }

    @Override
    public List<RecipeListResponseDto> findBookmarkOrRatingRecommendList(RecommendRecipeRequestDto requestDto) {
        User user = userService.findUserByUserId(requestDto.getUserId());
        List<Recipe> recommendedRecipes = recipeService.findRecipeListByRecipeIdIn(requestDto.getRecipeId());
        return recommendedRecipes.stream()
                .map(recipe -> new RecipeListResponseDto(recipe, recipeService.isRecipeBookmarked(user, recipe)))
                .collect(Collectors.toList());
    }

    private List<Recipe> findRecipeListContainingIngredients(IngredientDetectionRecipeRequestDto requestDto) {
        return recipeService.findByRecipeListByIngredientsContaining(requestDto.getIngredients().get(0))
                .stream()
                .filter(recipeService.filterPredicateForOptions(requestDto.getTime(), requestDto.getDifficulty(), requestDto.getComposition()))
                .collect(Collectors.toList());
    }

    private List<IngredientDetectionRecipeListResponseDto> sortRecipeListByPreference(User user, IngredientDetectionRecipeRequestDto requestDto, List<Recipe> recipeDetectionList) {
        return recipeDetectionList.stream()
                .filter(recipe -> containsAllIngredients(recipe, requestDto.getIngredients()))
                .map(recipe -> new IngredientDetectionRecipeListResponseDto(requestDto.getIngredients(), recipe,
                        Genre.containFavoriteGenre(recipe, user),
                        recipeService.isRecipeBookmarked(user, recipe)))
                .sorted(Comparator.comparing(IngredientDetectionRecipeListResponseDto::getIsFavoriteGenre, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    private boolean containsAllIngredients(Recipe recipe, List<String> ingredients) {
        return ingredients.stream().skip(1).allMatch(recipe.getIngredients()::contains);
    }

    private String addBookmarkParams(Long[] bookmarkList) {
        if (bookmarkList == null || bookmarkList.length == 0) {
            return "";
        }
        return "&bookmark_list=" + Arrays.stream(bookmarkList).map(String::valueOf).collect(Collectors.joining("&bookmark_list="));
    }

    private void updateUserFavoriteGenre(String genre, Long userId) {
        User user = userService.findUserByUserId(userId);
        user.updateGenre(genre);
    }
}