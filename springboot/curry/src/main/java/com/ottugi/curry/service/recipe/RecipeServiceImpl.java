package com.ottugi.curry.service.recipe;

import com.ottugi.curry.domain.recipe.Composition;
import com.ottugi.curry.domain.recipe.Difficulty;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.domain.recipe.Time;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.except.BaseCode;
import com.ottugi.curry.except.NotFoundException;
import com.ottugi.curry.service.lately.LatelyService;
import com.ottugi.curry.service.rank.RankService;
import com.ottugi.curry.service.user.UserService;
import com.ottugi.curry.util.PageConverter;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeResponseDto;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserService userService;
    private final LatelyService latelyService;
    private final RankService rankService;

    @Override
    public RecipeResponseDto findRecipeByUserIdAndRecipeId(Long userId, Long recipeId) {
        User user = userService.findUserByUserId(userId);
        Recipe recipe = findRecipeByRecipeId(recipeId);
        addRecipeToLately(user, recipe);
        boolean isBookmark = isRecipeBookmarked(user, recipe);
        return new RecipeResponseDto(recipe, isBookmark);
    }

    @Override
    public Page<RecipeListResponseDto> findRecipePageBySearchBox(Long userId, int page, int size,
                                                                 String name, String time, String difficulty, String composition) {
        User user = userService.findUserByUserId(userId);
        List<RecipeListResponseDto> recipeListResponseDtoList = findRecipesBySearchBox(user, name, time, difficulty, composition);
        updateOrAddRank(recipeListResponseDtoList, name);
        return PageConverter.convertToPage(recipeListResponseDtoList, page, size);
    }

    @Override
    public Recipe findRecipeByRecipeId(Long recipeId) {
        return recipeRepository.findByRecipeId(recipeId)
                .orElseThrow(() -> new NotFoundException(BaseCode.RECIPE_NOT_FOUND));
    }

    @Override
    public List<Recipe> findRecipeListByRecipeIdIn(List<Long> recipeIdList) {
        return recipeRepository.findByRecipeIdIn(recipeIdList);
    }

    @Override
    public List<Recipe> findByRecipeListByIngredientsContaining(String ingredients) {
        return recipeRepository.findByIngredientsContaining(ingredients);
    }

    @Override
    public Predicate<Recipe> filterPredicateForOptions(String time, String difficulty, String composition) {
        if (time.isBlank() && difficulty.isBlank() && composition.isBlank()) {
            return recipe -> true;
        }
        return recipe -> isRecipeMatchingCriteria(recipe, time, difficulty, composition);
    }

    @Override
    public Boolean isRecipeMatchingCriteria(Recipe recipe, String time, String difficulty, String composition) {
        if (time == null || time.isEmpty()) {
            return Difficulty.isDifficultyMatching(recipe, difficulty)
                    && Composition.isCompositionMatching(recipe, composition);
        }
        return Time.isTimeMatching(recipe, time)
                && Difficulty.isDifficultyMatching(recipe, difficulty)
                && Composition.isCompositionMatching(recipe, composition);
    }

    @Override
    public Boolean isRecipeBookmarked(User user, Recipe recipe) {
        return user.getBookmarkList()
                .stream()
                .anyMatch(bookmark -> bookmark.getRecipeId().equals(recipe));
    }

    private List<RecipeListResponseDto> findRecipesBySearchBox(User user, String name, String time, String difficulty, String composition) {
        return recipeRepository.findByNameContaining(name)
                .stream()
                .filter(filterPredicateForOptions(time, difficulty, composition))
                .map(recipe -> new RecipeListResponseDto(recipe, isRecipeBookmarked(user, recipe)))
                .collect(Collectors.toList());
    }

    private void addRecipeToLately(User user, Recipe recipe) {
        latelyService.addLately(user, recipe);
    }

    private void updateOrAddRank(List<RecipeListResponseDto> recipeListResponseDtoList, String name) {
        if (!recipeListResponseDtoList.isEmpty()) {
            rankService.updateOrAddRank(name);
        }
    }
}
