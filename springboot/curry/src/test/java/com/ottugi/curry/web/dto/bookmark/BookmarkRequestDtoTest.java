package com.ottugi.curry.web.dto.bookmark;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookmarkRequestDtoTest {

    @Test
    void BookmarkRequestDto_롬복() {

        // given
        Long userId = 1L;
        Long recipeId = 6842324L;

        // when
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto(userId, recipeId);

        // then
        assertEquals(bookmarkRequestDto.getUserId(), userId);
        assertEquals(bookmarkRequestDto.getRecipeId(), recipeId);
    }
}