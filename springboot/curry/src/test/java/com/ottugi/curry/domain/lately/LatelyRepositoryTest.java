package com.ottugi.curry.domain.lately;

import com.ottugi.curry.domain.recipe.*;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LatelyRepositoryTest {

    private User user;
    private Recipe recipe;
    private Lately lately;

    private LatelyRepository latelyRepository;
    private UserRepository userRepository;
    private RecipeRepository recipeRepository;

    @Autowired
    LatelyRepositoryTest(LatelyRepository latelyRepository, UserRepository userRepository, RecipeRepository recipeRepository) {
        this.latelyRepository = latelyRepository;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    @BeforeEach
    public void setUp() {
        // given
        user = userRepository.findById(USER_ID).get();
        recipe = recipeRepository.findByRecipeId(EXIST_RECIPE_ID).get();
    }

    @Test
    void 최근본레시피유저이름과레시피이름으로검색() {
        // when
        lately = latelyRepository.findByUserIdAndRecipeId(user, recipe);

        // then
        assertEquals(lately.getUserId().getId(), USER_ID);
        assertEquals(lately.getRecipeId().getRecipeId(), EXIST_RECIPE_ID);
    }

    @Test
    void 최근본레시피리스트유저이름으로정렬하여검색() {
        // when
        List<Lately> latelyList = latelyRepository.findByUserIdOrderByIdDesc(user);

        // then
        lately = latelyList.get(1);
        assertEquals(lately.getUserId().getId(), USER_ID);
        assertEquals(lately.getRecipeId().getRecipeId(), EXIST_RECIPE_ID);
    }

    @Test
    void 유저이름으로최근본레시피횟수검색() {
        // when
        int userIdCount = latelyRepository.countByUserId(user);

        // then
        assertEquals(userIdCount, 2);
    }
}