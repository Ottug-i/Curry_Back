package com.ottugi.curry.service.recipe;

import com.ottugi.curry.domain.recipe.Composition;
import com.ottugi.curry.domain.recipe.Difficulty;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.domain.recipe.Servings;
import com.ottugi.curry.domain.recipe.Time;
import com.ottugi.curry.web.dto.recipe.RecipeSaveRequestDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RecipeCsvWriter implements ItemWriter<RecipeSaveRequestDto> {
    private final RecipeRepository recipeRepository;

    @Override
    public void write(List<? extends RecipeSaveRequestDto> list) {
        List<Recipe> recipeList = new ArrayList<>();
        list.forEach(csvInfo -> {
            Recipe recipe = Recipe.builder()
                    .recipeId(csvInfo.getRecipeId())
                    .name(csvInfo.getName())
                    .composition(Composition.ofComposition(csvInfo.getComposition()))
                    .ingredients(csvInfo.getIngredients())
                    .servings(Servings.ofServings(csvInfo.getServings()))
                    .difficulty(Difficulty.ofDifficulty(csvInfo.getDifficulty()))
                    .thumbnail(csvInfo.getThumbnail())
                    .time(Time.ofTime(csvInfo.getTime()))
                    .orders(csvInfo.getOrders())
                    .photo(csvInfo.getPhoto())
                    .genre(csvInfo.getGenre())
                    .build();
            recipeList.add(recipe);
        });
        recipeRepository.saveAll(recipeList);
    }
}
