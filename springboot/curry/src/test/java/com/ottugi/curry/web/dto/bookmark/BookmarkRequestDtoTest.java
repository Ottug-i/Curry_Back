package com.ottugi.curry.web.dto.bookmark;

import org.junit.jupiter.api.Test;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class BookmarkRequestDtoTest {

    @Test
    void BookmarkRequestDto_롬복() {
        // when
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto(USER_ID, EXIST_RECIPE_ID);

        // then
        assertEquals(bookmarkRequestDto.getUserId(), USER_ID);
        assertEquals(bookmarkRequestDto.getRecipeId(), EXIST_RECIPE_ID);
    }
}