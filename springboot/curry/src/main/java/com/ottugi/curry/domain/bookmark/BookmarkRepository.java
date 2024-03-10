package com.ottugi.curry.domain.bookmark;

import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Boolean existsByUserIdAndRecipeId(User user, Recipe recipe);

    @Transactional
    void deleteByUserIdAndRecipeId(User user, Recipe recipe);
}
