package com.ottugi.curry.domain.lately;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.TestObjectFactory;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class LatelyRepositoryTest {
    private User user;
    private Recipe recipe;

    @Autowired
    private LatelyRepository latelyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    public void setUp() {
        user = TestObjectFactory.initUser();
        user = userRepository.save(user);

        recipe = TestObjectFactory.initRecipe();
        recipe = recipeRepository.save(recipe);

        Lately lately = TestObjectFactory.initLately();
        lately.setUser(user);
        lately.setRecipe(recipe);
        latelyRepository.save(lately);
    }

    @AfterEach
    public void clean() {
        latelyRepository.deleteAll();
        recipeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 아이디에 따라 가장 최근 본 레시피 확인")
    void testFindTop1ByUserIdOrderByIdDesc() {
        Lately foundLately = latelyRepository.findTop1ByUserIdOrderByIdDesc(user);

        assertEquals(user.getId(), foundLately.getUserId().getId());
        assertEquals(recipe.getId(), foundLately.getRecipeId().getId());
    }
}