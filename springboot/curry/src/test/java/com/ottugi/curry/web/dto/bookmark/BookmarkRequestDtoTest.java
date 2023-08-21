package com.ottugi.curry.web.dto.bookmark;

import org.junit.jupiter.api.Test;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class BookmarkRequestDtoTest {

    @Test
    void BookmarkRequestDto_롬복() {
        // when
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto(USER_ID, RECIPE_ID);

        // then
        assertEquals(USER_ID, bookmarkRequestDto.getUserId());
        assertEquals(RECIPE_ID, bookmarkRequestDto.getRecipeId());
    }
}