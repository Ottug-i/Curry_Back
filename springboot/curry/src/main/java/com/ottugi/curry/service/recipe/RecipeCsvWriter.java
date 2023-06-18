package com.ottugi.curry.service.recipe;

import com.ottugi.curry.domain.recipe.*;
import com.ottugi.curry.web.dto.recipe.RecipeSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RecipeCsvWriter implements ItemWriter<RecipeSaveRequestDto> {

    private final RecipeRepository recipeRepository;

    @Override
    public void write(List<? extends RecipeSaveRequestDto> list) throws Exception {

        List<Recipe> recipeList = new ArrayList<>();

        list.forEach(getRecipe -> {

            Recipe recipe = new Recipe();
            recipe.setId(getRecipe.getId());
            recipe.setName(getRecipe.getName());
            recipe.setComposition(Composition.ofComposition(getRecipe.getComposition()));
            recipe.setIngredients(getRecipe.getIngredients());
            recipe.setServings(Servings.ofServings(getRecipe.getServings()));
            recipe.setDifficulty(Difficulty.ofDifficulty(getRecipe.getDifficulty()));
            recipe.setThumbnail(getRecipe.getThumbnail());
            recipe.setTime(Time.ofTime(getRecipe.getTime()));
            recipe.setOrders(getRecipe.getOrders());
            recipe.setPhoto(getRecipe.getPhoto());

            recipeList.add(recipe);
        });

        recipeRepository.saveAll(recipeList);
    }
}
