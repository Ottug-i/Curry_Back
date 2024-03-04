package com.ottugi.curry.domain.lately;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.domain.recipe.RecipeTest;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.domain.user.UserTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LatelyRepositoryTest {
    private Lately lately;

    @Autowired
    private LatelyRepository latelyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    public void setUp() {
        lately = LatelyTest.initLately();
        lately.setUser(userRepository.save(UserTest.initUser()));
        lately.setRecipe(recipeRepository.save(RecipeTest.initRecipe()));
        lately = latelyRepository.save(lately);
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
        Lately foundLately = latelyRepository.findTop1ByUserIdOrderByIdDesc(lately.getUserId());

        assertEquals(lately.getUserId().getId(), foundLately.getUserId().getId());
        assertEquals(lately.getRecipeId().getId(), foundLately.getRecipeId().getId());
    }
}