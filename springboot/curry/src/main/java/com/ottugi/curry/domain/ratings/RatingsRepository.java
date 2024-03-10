package com.ottugi.curry.domain.ratings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RatingsRepository extends JpaRepository<Ratings, Long> {
    Boolean existsByUserIdAndRecipeId(Long userId, Long recipeId);

    Ratings findByUserIdAndRecipeId(Long userId, Long recipeId);

    @Transactional
    void deleteByUserIdAndRecipeId(Long userId, Long recipeId);
}
