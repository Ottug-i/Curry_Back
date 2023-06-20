package com.ottugi.curry.domain.recipe;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByIdIn(List<Long> recipeId);
    List<Recipe> findByName(String name);
}
