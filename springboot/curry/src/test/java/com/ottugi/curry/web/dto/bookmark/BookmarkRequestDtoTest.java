package com.ottugi.curry.web.dto.bookmark;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ottugi.curry.ValidatorUtil;
import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.bookmark.BookmarkTest;
import com.ottugi.curry.domain.recipe.RecipeTest;
import com.ottugi.curry.domain.user.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BookmarkRequestDtoTest {
    private final ValidatorUtil<BookmarkRequestDto> validatorUtil = new ValidatorUtil<>();

    public static BookmarkRequestDto initBookmarkRequestDto(Bookmark bookmark) {
        return BookmarkRequestDto.builder()
                .userId(bookmark.getUserId().getId())
                .recipeId(bookmark.getRecipeId().getRecipeId())
                .build();
    }

    private Bookmark bookmark;

    @BeforeEach
    public void setUp() {
        bookmark = BookmarkTest.initBookmark();
        bookmark.setUser(UserTest.initUser());
        bookmark.setRecipe(RecipeTest.initRecipe());
    }

    @Test
    @DisplayName("BookmarkRequestDto 생성 테스트")
    void testBookmarkRequestDto() {
        BookmarkRequestDto bookmarkRequestDto = initBookmarkRequestDto(bookmark);

        assertEquals(bookmark.getUserId().getId(), bookmarkRequestDto.getUserId());
        assertEquals(bookmark.getRecipeId().getRecipeId(), bookmarkRequestDto.getRecipeId());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto();

        assertNotNull(bookmarkRequestDto);
        assertNull(bookmarkRequestDto.getUserId());
        assertNull(bookmarkRequestDto.getRecipeId());
    }

    @Test
    @DisplayName("회원 아이디 유효성 검증 테스트")
    void userId_validation() {
        BookmarkRequestDto bookmarkRequestDto = BookmarkRequestDto.builder()
                .userId(null)
                .recipeId(bookmark.getRecipeId().getRecipeId())
                .build();

        validatorUtil.validate(bookmarkRequestDto);
    }

    @Test
    @DisplayName("레시피 아이디 유효성 검증 테스트")
    void recipeId_validation() {
        BookmarkRequestDto bookmarkRequestDto = BookmarkRequestDto.builder()
                .userId(bookmark.getUserId().getId())
                .recipeId(null)
                .build();

        validatorUtil.validate(bookmarkRequestDto);
    }
}