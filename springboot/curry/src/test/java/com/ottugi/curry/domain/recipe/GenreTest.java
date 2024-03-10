package com.ottugi.curry.domain.recipe;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserTest;
import com.ottugi.curry.except.InvalidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GenreTest {
    @Test
    @DisplayName("올바른 레시피 장르 열거형 값 테스트")
    void testFindByValidIngredientNumber() {
        String validGenreNumber = Genre.MEAT.getIngredientNumbers().get(0);

        Genre genre = Genre.findByIngredientNumber(validGenreNumber);

        assertNotNull(genre);
        assertEquals(Genre.MEAT, genre);
    }

    @Test
    @DisplayName("올바르지 않은 레시피 장르 열거형 값 테스트")
    void testFindByInvalidIngredientNumber() {
        String invalidGenreNumber = "ing26";

        assertThatThrownBy(() -> Genre.findByIngredientNumber(invalidGenreNumber))
                .isInstanceOf(InvalidException.class);
    }

    @Test
    @DisplayName("레시피의 메인 장르 찾기 테스트")
    void testFindMainGenreCharacter() {
        Recipe recipe = RecipeTest.initRecipe();

        String mainGenre = Genre.findMainGenreCharacter(recipe);

        assertEquals("vegetable", mainGenre);
    }

    @Test
    @DisplayName("레시피 장르에 회원이 가장 좋아하는 장르가 포함되어 있는지 확인 테스트")
    void testContainFavoriteGenre() {
        Recipe recipe = RecipeTest.initRecipe();
        User user = UserTest.initUser();

        Boolean result = Genre.containFavoriteGenre(recipe, user);

        assertFalse(result);
    }
}