package com.ottugi.curry.domain.lately;

import com.ottugi.curry.TestConstants;
import com.ottugi.curry.domain.recipe.*;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor
class LatelyRepositoryTest {

    private User user;
    private Recipe recipe;
    private Lately lately;

    private final TestConstants testConstants;
    private final LatelyRepository latelyRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    @BeforeEach
    public void setUp() {
        // given
        user = userRepository.findById(USER_ID).get();
        recipe = recipeRepository.findByRecipeId(RECIPE_ID).get();
    }

    @Test
    void 최근본레시피유저이름과레시피이름으로검색() {
        // when
        lately = latelyRepository.findByUserIdAndRecipeId(user, recipe);

        // then
        assertEquals(lately.getUserId().getId(), USER_ID);
        assertEquals(lately.getRecipeId().getRecipeId(), RECIPE_ID);
    }

    @Test
    void 최근본레시피리스트유저이름으로정렬하여검색() {
        // when
        List<Lately> latelyList = latelyRepository.findByUserIdOrderByIdDesc(user);

        // then
        lately = latelyList.get(1);
        assertEquals(lately.getUserId().getId(), USER_ID);
        assertEquals(lately.getRecipeId().getRecipeId(), RECIPE_ID);
    }

    @Test
    void 유저이름으로최근본레시피횟수검색() {
        // when
        int userIdCount = latelyRepository.countByUserId(user);

        // then
        assertEquals(userIdCount, 2);
    }
}