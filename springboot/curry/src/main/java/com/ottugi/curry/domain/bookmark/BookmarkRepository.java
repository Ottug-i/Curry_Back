package com.ottugi.curry.domain.bookmark;

import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Boolean existsByUserIdAndRecipeId(User user, Recipe recipe);

    Bookmark findByUserIdAndRecipeId(User user, Recipe recipe);

    List<Bookmark> findByUserId(User user);

    @Transactional
    void deleteByUserIdAndRecipeId(User user, Recipe recipe);
}
