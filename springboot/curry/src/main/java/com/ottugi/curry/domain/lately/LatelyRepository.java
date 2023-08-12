package com.ottugi.curry.domain.lately;

import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LatelyRepository extends JpaRepository<Lately, Long> {
    Lately findByUserIdAndRecipeId(User user, Recipe recipe);
    List<Lately> findByUserIdOrderByIdDesc(User user);
    int countByUserId(User user);
}
