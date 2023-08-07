package com.ottugi.curry.domain.recipe;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByIngredientsContaining(String ingredients);
    List<Recipe> findByNameContaining(String name);
    List<Recipe> findByRecipeIdIn(List<Long> recipeId);
    Optional<Recipe> findByRecipeId(Long recipeId);
}
