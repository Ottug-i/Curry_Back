package com.ottugi.curry.service.recipe;

import com.ottugi.curry.domain.recipe.Composition;
import com.ottugi.curry.domain.recipe.Difficulty;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.domain.recipe.Servings;
import com.ottugi.curry.domain.recipe.Time;
import com.ottugi.curry.web.dto.recipe.RecipeSaveRequestDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RecipeCsvWriter implements ItemWriter<RecipeSaveRequestDto> {
    private static final int BATCH_SIZE = 1000;

    private final RecipeRepository recipeRepository;

    @Override
    public void write(List<? extends RecipeSaveRequestDto> dtoList) {
        List<Recipe> recipeList = dtoListToRecipeList(dtoList);
        saveRecipeListInBatch(recipeList);
    }

    private List<Recipe> dtoListToRecipeList(List<? extends RecipeSaveRequestDto> dtoList) {
        return dtoList.stream()
                .map(this::createRecipe)
                .collect(Collectors.toList());
    }

    private Recipe createRecipe(RecipeSaveRequestDto requestDto) {
        return Recipe.builder()
                .recipeId(requestDto.getRecipeId())
                .name(requestDto.getName())
                .composition(Composition.ofComposition(requestDto.getComposition()))
                .ingredients(requestDto.getIngredients())
                .servings(Servings.ofServings(requestDto.getServings()))
                .difficulty(Difficulty.ofDifficulty(requestDto.getDifficulty()))
                .thumbnail(requestDto.getThumbnail())
                .time(Time.ofTime(requestDto.getTime()))
                .orders(requestDto.getOrders())
                .photo(requestDto.getPhoto())
                .genre(requestDto.getGenre())
                .build();
    }

    private void saveRecipeListInBatch(List<Recipe> recipeList) {
        for (int i = 0; i < recipeList.size(); i += BATCH_SIZE) {
            int endIndex = Math.min(i + BATCH_SIZE, recipeList.size());
            List<Recipe> batch = recipeList.subList(i, endIndex);
            recipeRepository.saveAll(batch);
        }
    }
}
