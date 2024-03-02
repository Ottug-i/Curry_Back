package com.ottugi.curry.domain.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ottugi.curry.TestObjectFactory;
import com.ottugi.curry.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GenreTest {
    @Test
    @DisplayName("올바른 레시피 장르 열거형 값 테스트")
    void testOfValidGenre() {
        String validGenreNumber = Genre.MEAT.getIngredientNumber().get(0);

        Genre genre = Genre.ofGenre(validGenreNumber);

        assertNotNull(genre);
        assertEquals(Genre.MEAT, genre);
    }

    @Test
    @DisplayName("올바르지 않은 레시피 장르 열거형 값 테스트")
    void testOfInvalidGenre() {
        String invalidGenreNumber = "ing26";

        Genre genre = Genre.ofGenre(invalidGenreNumber);

        assertNull(genre);
    }

    @Test
    @DisplayName("레시피의 메인 장르 찾기 테스트")
    void testFindMainGenre() {
        Recipe recipe = TestObjectFactory.initRecipe();

        String mainGenre = Genre.findMainGenre(recipe);

        assertEquals("ing13", mainGenre);
    }

    @Test
    @DisplayName("레시피 장르에 회원이 가장 좋아하는 장르가 포함되어 있는지 확인 테스트")
    void testContainFavoriteGenre() {
        Recipe recipe = TestObjectFactory.initRecipe();
        User user = TestObjectFactory.initUser();

        Boolean result = Genre.containFavoriteGenre(recipe, user);

        assertFalse(result);
    }
}