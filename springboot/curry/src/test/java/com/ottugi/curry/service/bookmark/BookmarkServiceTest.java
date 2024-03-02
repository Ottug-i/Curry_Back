package com.ottugi.curry.service.bookmark;

import static com.ottugi.curry.TestConstants.PAGE;
import static com.ottugi.curry.TestConstants.SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ottugi.curry.TestObjectFactory;
import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.bookmark.BookmarkRepository;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.service.recipe.RecipeService;
import com.ottugi.curry.service.user.UserService;
import com.ottugi.curry.web.dto.bookmark.BookmarkListResponseDto;
import com.ottugi.curry.web.dto.bookmark.BookmarkRequestDto;
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
    private BookmarkRequestDto bookmarkRequestDto;

    @Mock
    private UserService userService;

    @Mock
    private RecipeService recipeService;

    @Mock
    private BookmarkRepository bookmarkRepository;

    @InjectMocks
    private BookmarkService bookmarkService;

    @BeforeEach
    public void setUp() {
        bookmark = TestObjectFactory.initBookmark();
        bookmark.setUser(mock(User.class));
        bookmark.setRecipe(mock(Recipe.class));

        bookmarkRequestDto = TestObjectFactory.initBookmarkRequestDto(bookmark);
    }

    @Test
    @DisplayName("북마크 추가 테스트")
    void testAddBookmark() {
        when(userService.findUserByUserId(anyLong())).thenReturn(bookmark.getUserId());
        when(recipeService.findRecipeByRecipeId(anyLong())).thenReturn(bookmark.getRecipeId());
        when(bookmarkRepository.existsByUserIdAndRecipeId(any(User.class), any(Recipe.class))).thenReturn(false);
        when(bookmarkRepository.save(any(Bookmark.class))).thenReturn(bookmark);

        Boolean result = bookmarkService.addOrRemoveBookmark(bookmarkRequestDto);

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

        Boolean result = bookmarkService.addOrRemoveBookmark(bookmarkRequestDto);

        assertTrue(result);

        verify(userService, times(1)).findUserByUserId(anyLong());
        verify(recipeService, times(1)).findRecipeByRecipeId(anyLong());
        verify(bookmarkRepository, times(1)).existsByUserIdAndRecipeId(any(User.class), any(Recipe.class));
        verify(bookmarkRepository, times(1)).deleteByUserIdAndRecipeId(any(User.class), any(Recipe.class));
    }

    @Test
    @DisplayName("회원 아이디에 따른 북마크 목록 페이지 조회 테스트")
    void testFindBookmarkPageByUserId() {
        when(userService.findUserByUserId(anyLong())).thenReturn(bookmark.getUserId());

        Page<BookmarkListResponseDto> result = bookmarkService.findBookmarkPageByUserId(bookmark.getUserId().getId(), PAGE, SIZE);

        assertEquals(1, result.getTotalElements());

        verify(userService, times(1)).findUserByUserId(anyLong());
    }

    @Test
    @DisplayName("옵션에 따른 북마크 레시피 목록 페이지 조회 테스트")
    void testFindBookmarkPageByOption() {
        when(userService.findUserByUserId(anyLong())).thenReturn(bookmark.getUserId());
        when(recipeService.isRecipeMatchingCriteria(any(Recipe.class), anyString(), anyString(), anyString())).thenReturn(true);

        Page<BookmarkListResponseDto> result = bookmarkService.findBookmarkPageByOption(bookmark.getUserId().getId(), PAGE, SIZE,
                bookmark.getRecipeId().getName(), bookmark.getRecipeId().getTime().getTimeName(),
                bookmark.getRecipeId().getDifficulty().getDifficulty(), bookmark.getRecipeId().getComposition().getComposition());

        assertEquals(1, result.getTotalElements());

        verify(userService, times(1)).findUserByUserId(anyLong());
        verify(recipeService, times(1)).isRecipeMatchingCriteria(any(Recipe.class), anyString(), anyString(), anyString());
    }
}