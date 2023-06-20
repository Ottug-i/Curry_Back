package com.ottugi.curry.service.recipe;

import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeRequestDto;
import com.ottugi.curry.web.dto.recipe.RecipeResponseDto;

import java.util.List;

public interface RecipeService {

    List<RecipeListResponseDto> getRecipeList(RecipeRequestDto recipeRequestDto);
    RecipeResponseDto getRecipeDetail(Long userId, Long recipeId);
    List<RecipeListResponseDto> searchByBox(Long userId, String name, String time, String difficulty, String composition);
}
