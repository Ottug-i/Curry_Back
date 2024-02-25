package com.ottugi.curry.service.recipe;

import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeResponseDto;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RecipeService {
    @Transactional
    RecipeResponseDto findRecipeByUserIdAndRecipeId(Long userId, Long recipeId);

    Page<RecipeListResponseDto> findRecipePageBySearchBox(Long userId, int page, int size,
                                                          String name, String time, String difficulty, String composition);

    Recipe findRecipeByRecipeId(Long recipeId);

    List<Recipe> findRecipeListByRecipeIdIn(List<Long> recipeIdList);

    List<Recipe> findByRecipeListByIngredientsContaining(String ingredients);

    Predicate<Recipe> filterPredicateForOptions(String time, String difficulty, String composition);

    Boolean isRecipeMatching(Recipe recipe, String time, String difficulty, String composition);
}
