package com.ottugi.curry.service.bookmark;

import static com.ottugi.curry.domain.bookmark.BookmarkTest.IS_BOOKMARK;
import static com.ottugi.curry.domain.recipe.RecipeTest.COMPOSITION;
import static com.ottugi.curry.domain.recipe.RecipeTest.DIFFICULTY;
import static com.ottugi.curry.domain.recipe.RecipeTest.PAGE;
import static com.ottugi.curry.domain.recipe.RecipeTest.SIZE;
import static com.ottugi.curry.domain.recipe.RecipeTest.TIME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.bookmark.BookmarkRepository;
import com.ottugi.curry.domain.bookmark.BookmarkTest;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.service.recipe.RecipeService;
import com.ottugi.curry.service.user.UserService;
import com.ottugi.curry.web.dto.bookmark.BookmarkListResponseDto;
import com.ottugi.curry.web.dto.bookmark.BookmarkRequestDto;
import com.ottugi.curry.web.dto.bookmark.BookmarkRequestDtoTest;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

@ExtendWith(MockitoExtension.class)
class BookmarkServiceTest {
    private Bookmark bookmark;

    @Mock
    private UserService userService;
    @Mock
    private RecipeService recipeService;
    @Mock
    private BookmarkRepository bookmarkRepository;
    @InjectMocks
    private BookmarkServiceImpl bookmarkService;

    @BeforeEach
    public void setUp() {
        bookmark = BookmarkTest.initBookmark();
        bookmark.setUser(mock(User.class));
        bookmark.setRecipe(mock(Recipe.class));
    }

    @Test
    @DisplayName("북마크 추가 테스트")
    void testAddBookmark() {
        when(userService.findUserByUserId(anyLong())).thenReturn(bookmark.getUserId());
        when(recipeService.findRecipeByRecipeId(anyLong())).thenReturn(bookmark.getRecipeId());
        when(bookmarkRepository.existsByUserIdAndRecipeId(any(User.class), any(Recipe.class))).thenReturn(false);
        when(bookmarkRepository.save(any(Bookmark.class))).thenReturn(bookmark);

        BookmarkRequestDto bookmarkRequestDto = BookmarkRequestDtoTest.initBookmarkRequestDto(bookmark);
        boolean result = bookmarkService.addOrRemoveBookmark(bookmarkRequestDto);

        assertTrue(result);

        verify(userService, times(1)).findUserByUserId(anyLong());
        verify(recipeService, times(1)).findRecipeByRecipeId(anyLong());
        verify(bookmarkRepository, times(1)).existsByUserIdAndRecipeId(any(User.class), any(Recipe.class));
        verify(bookmarkRepository, times(1)).save(any(Bookmark.class));
    }

    @Test
    @DisplayName("북마크 삭제 테스트")
    void testRemoveBookmark() {
        when(userService.findUserByUserId(anyLong())).thenReturn(bookmark.getUserId());
        when(recipeService.findRecipeByRecipeId(anyLong())).thenReturn(bookmark.getRecipeId());
        when(bookmarkRepository.existsByUserIdAndRecipeId(any(User.class), any(Recipe.class))).thenReturn(true);
        doNothing().when(bookmarkRepository).deleteByUserIdAndRecipeId(any(User.class), any(Recipe.class));

        BookmarkRequestDto bookmarkRequestDto = BookmarkRequestDtoTest.initBookmarkRequestDto(bookmark);
        boolean result = bookmarkService.addOrRemoveBookmark(bookmarkRequestDto);

        assertFalse(result);

        verify(userService, times(1)).findUserByUserId(anyLong());
        verify(recipeService, times(1)).findRecipeByRecipeId(anyLong());
        verify(bookmarkRepository, times(1)).existsByUserIdAndRecipeId(any(User.class), any(Recipe.class));
        verify(bookmarkRepository, times(1)).deleteByUserIdAndRecipeId(any(User.class), any(Recipe.class));
    }

    @Test
    @DisplayName("회원 아이디에 따른 북마크 목록 페이지 조회 테스트")
    void testFindBookmarkPageByUserId() {
        when(userService.findUserByUserId(anyLong())).thenReturn(bookmark.getUserId());
        when(bookmark.getRecipeId().getTime()).thenReturn(TIME);
        when(bookmark.getRecipeId().getDifficulty()).thenReturn(DIFFICULTY);
        when(bookmark.getRecipeId().getComposition()).thenReturn(COMPOSITION);
        when(bookmark.getUserId().getBookmarkList()).thenReturn(Collections.singletonList(bookmark));

        Page<BookmarkListResponseDto> result = bookmarkService.findBookmarkPageByUserId(bookmark.getUserId().getId(), PAGE, SIZE);

        assertEquals(1, result.getTotalElements());
        assertBookmarkListResponseDto(result.getContent().get(0));

        verify(userService, times(1)).findUserByUserId(anyLong());
    }

    @Test
    @DisplayName("옵션에 따른 북마크 레시피 목록 페이지 조회 테스트")
    void testFindBookmarkPageByOption() {
        when(userService.findUserByUserId(anyLong())).thenReturn(bookmark.getUserId());
        when(bookmark.getRecipeId().getTime()).thenReturn(TIME);
        when(bookmark.getRecipeId().getDifficulty()).thenReturn(DIFFICULTY);
        when(bookmark.getRecipeId().getComposition()).thenReturn(COMPOSITION);
        when(bookmark.getUserId().getBookmarkList()).thenReturn(Collections.singletonList(bookmark));
        when(recipeService.isRecipeMatchedCriteria(any(Recipe.class), anyString(), anyString(), anyString())).thenReturn(true);

        Page<BookmarkListResponseDto> result = bookmarkService.findBookmarkPageByOption(bookmark.getUserId().getId(), PAGE, SIZE,
                bookmark.getRecipeId().getName(), bookmark.getRecipeId().getTime().getTimeName(),
                bookmark.getRecipeId().getDifficulty().getDifficultyName(), bookmark.getRecipeId().getComposition().getCompositionName());

        assertEquals(1, result.getTotalElements());
        assertBookmarkListResponseDto(result.getContent().get(0));

        verify(userService, times(1)).findUserByUserId(anyLong());
        verify(recipeService, times(1)).isRecipeMatchedCriteria(any(Recipe.class), anyString(), anyString(), anyString());
    }

    private void assertBookmarkListResponseDto(BookmarkListResponseDto resultDto) {
        assertNotNull(resultDto);
        assertEquals(bookmark.getRecipeId().getRecipeId(), resultDto.getRecipeId());
        assertEquals(bookmark.getRecipeId().getName(), resultDto.getName());
        assertEquals(bookmark.getRecipeId().getThumbnail(), resultDto.getThumbnail());
        assertEquals(bookmark.getRecipeId().getTime().getTimeName(), resultDto.getTime());
        assertEquals(bookmark.getRecipeId().getDifficulty().getDifficultyName(), resultDto.getDifficulty());
        assertEquals(bookmark.getRecipeId().getComposition().getCompositionName(), resultDto.getComposition());
        assertEquals(bookmark.getRecipeId().getIngredients(), resultDto.getIngredients());
        assertEquals(IS_BOOKMARK, resultDto.getIsBookmark());
    }
}