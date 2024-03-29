package com.ottugi.curry.domain.recipe;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByIngredientsContaining(String ingredients);

    List<Recipe> findByNameContaining(String name);

    List<Recipe> findByRecipeIdIn(List<Long> recipeId);

    List<Recipe> findByIdIn(List<Long> id);

    Optional<Recipe> findByRecipeId(Long recipeId);
}
