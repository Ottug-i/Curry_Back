package com.ottugi.curry.domain.ratings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingsRepository extends JpaRepository<Ratings, Long> {
    Ratings findByUserIdAndRecipeId(Long userId, Long recipeId);
}