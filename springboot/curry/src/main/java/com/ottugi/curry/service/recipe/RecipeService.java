package com.ottugi.curry.service.recipe;

import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeRequestDto;
import com.ottugi.curry.web.dto.recipe.RecipeResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecipeService {

    Page<RecipeListResponseDto> getRecipeList(RecipeRequestDto recipeRequestDto);
    RecipeResponseDto getRecipeDetail(Long userId, Long recipeId);
    List<RecipeListResponseDto> searchByBox(Long userId, String name, String time, String difficulty, String composition);
}
