package com.ottugi.curry.domain.lately;

import com.ottugi.curry.domain.recipe.*;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LatelyRepositoryTest {

    // 이미 DB에 저장되어있는 데이터 사용
    private final Long userId = 1L;
    private final Long recipeId = 6842324L;

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

        // given
        user = userRepository.findById(userId).orElseThrow();
        recipe = recipeRepository.findByRecipeId(recipeId).orElseThrow();
    }

    @Test
    void 최근본레시피유저이름과레시피이름으로검색() {

        // when
        Lately findLately = latelyRepository.findByUserIdAndRecipeId(user, recipe);

        // then
        assertEquals(findLately.getUserId().getId(), userId);
        assertEquals(findLately.getRecipeId().getRecipeId(), recipeId);
    }

    @Test
    void 최근본레시피리스트유저이름으로정렬하여검색() {

        // when
        List<Lately> latelyList = latelyRepository.findByUserIdOrderByIdDesc(user);

        // then
        Lately findLately = latelyList.get(1);
        assertEquals(findLately.getUserId().getId(), userId);
        assertEquals(findLately.getRecipeId().getRecipeId(), recipeId);
    }

    @Test
    void 유저이름으로최근본레시피횟수검색() {

        // when
        int userIdCount = latelyRepository.countByUserId(user);

        // then
        assertEquals(userIdCount, 2);
    }
}