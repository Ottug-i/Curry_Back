package com.ottugi.curry.domain.lately;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ottugi.curry.TestObjectFactory;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LatelyTest {
    private Lately lately;

    @BeforeEach
    public void setUp() {
        lately = TestObjectFactory.initLately();
    }

    @Test
    @DisplayName("최근 본 레시피 추가 테스트")
    void testLately() {
        assertNotNull(lately);
    }

    @Test
    @DisplayName("최근 본 레시피의 회원 연관관계 설정 테스트")
    void testSetUser() {
        User user = mock(User.class);
        lately.setUser(user);

        assertEquals(lately.getUserId(), user);
        verify(user, times(1)).getBookmarkList();
    }

    @Test
    @DisplayName("최근 본 레시피의 레시피 연관관계 설정 테스트")
    void testSetRecipe() {
        Recipe recipe = mock(Recipe.class);
        lately.setRecipe(recipe);

        assertEquals(lately.getRecipeId(), recipe);
    }
}