package com.ottugi.curry.service.bookmark;

import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.bookmark.BookmarkRepository;
import com.ottugi.curry.domain.recipe.*;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.service.CommonService;
import com.ottugi.curry.web.dto.bookmark.BookmarkListResponseDto;
import com.ottugi.curry.web.dto.bookmark.BookmarkRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookmarkServiceTest {

    private User user;
    private Recipe recipe;
    private Bookmark bookmark;
    private BookmarkRequestDto bookmarkRequestDto;
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @Mock
    private CommonService commonService;

    @Mock
    private BookmarkRepository bookmarkRepository;

    @InjectMocks
    private BookmarkService bookmarkService;

    @BeforeEach
    public void setUp() {
        // given
        user = new User();
        recipe = new Recipe();
        bookmark = new Bookmark();
    }

    @Test
    void 북마크추가() {
        // given
        bookmarkRequestDto = new BookmarkRequestDto(USER_ID, RECIPE_ID);

        // when
        when(commonService.findByUserId(USER_ID)).thenReturn(user);
        when(commonService.findByRecipeId(RECIPE_ID)).thenReturn(recipe);
        when(commonService.isBookmarked(user, recipe)).thenReturn(false);

        // then
        assertTrue(bookmarkService.addOrRemoveBookmark(bookmarkRequestDto));
        verify(bookmarkRepository, times(1)).save(bookmark);
    }

    @Test
    void 북마크삭제() {
        // given
        bookmarkRequestDto = new BookmarkRequestDto(USER_ID, RECIPE_ID);

        // when
        when(commonService.findByUserId(USER_ID)).thenReturn(user);
        when(commonService.findByRecipeId(RECIPE_ID)).thenReturn(recipe);
        when(commonService.isBookmarked(user, recipe)).thenReturn(true);

        // then
        assertTrue(bookmarkService.addOrRemoveBookmark(bookmarkRequestDto));
        verify(bookmarkRepository, times(1)).delete(bookmark);
    }

    @Test
    void 북마크리스트조회() {
        // when
        when(commonService.findByUserId(USER_ID)).thenReturn(user);
        when(commonService.findBookmarkByUser(user)).thenReturn(bookmarkList);

        Page<BookmarkListResponseDto> response = bookmarkService.getBookmarkAll(USER_ID, PAGE, SIZE);

        // then
        assertEquals(response.getTotalElements(), 0);
    }

    @Test
    void 북마크검색() {
        // when
        when(commonService.findByUserId(USER_ID)).thenReturn(user);
        when(commonService.findBookmarkByUser(user)).thenReturn(bookmarkList);
        when(commonService.isRecipeMatching(recipe, TIME.getTimeName(), DIFFICULTY.getDifficulty(), COMPOSITION.getComposition())).thenReturn(true);

        Page<BookmarkListResponseDto> response = bookmarkService.searchBookmark(USER_ID, PAGE, SIZE, NAME, TIME.getTimeName(), DIFFICULTY.getDifficulty(), COMPOSITION.getComposition());

        // then
        assertEquals(response.getTotalElements(), 0);
    }
}