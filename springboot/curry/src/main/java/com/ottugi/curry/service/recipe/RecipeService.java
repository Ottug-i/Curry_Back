package com.ottugi.curry.service.recipe;

import com.ottugi.curry.web.dto.recipe.RecipeIngListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeRequestDto;
import com.ottugi.curry.web.dto.recipe.RecipeResponseDto;
import com.ottugi.curry.web.dto.recommend.RecommendRequestDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecipeService {

    Page<RecipeIngListResponseDto> getRecipeList(RecipeRequestDto recipeRequestDto);
    List<RecipeListResponseDto> getRecommendList(RecommendRequestDto recommendRequestDto);
    RecipeResponseDto getRecipeDetail(Long userId, Long recipeId);
    Page<RecipeListResponseDto> searchByBox(Long userId, int page, int size, String name, String time, String difficulty, String composition);
}
