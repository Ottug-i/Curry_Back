package com.ottugi.curry.service.recipe;

import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeResponseDto;
import org.springframework.data.domain.Page;

public interface RecipeService {

    RecipeResponseDto getRecipeDetail(Long userId, Long recipeId);
    Page<RecipeListResponseDto> searchByBox(Long userId, int page, int size, String name, String time, String difficulty, String composition);
}
