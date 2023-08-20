package com.ottugi.curry.service.bookmark;

import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.bookmark.BookmarkRepository;
import com.ottugi.curry.domain.recipe.*;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.service.CommonService;
import com.ottugi.curry.web.dto.bookmark.BookmarkListResponseDto;
import com.ottugi.curry.web.dto.bookmark.BookmarkRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookmarkServiceTest {

    private User user;

    private Recipe recipe1;
    private Recipe recipe2;

    private Bookmark bookmark1;
    private Bookmark bookmark2;

    private BookmarkRequestDto bookmarkRequestDto;

    private List<Bookmark> bookmarkList = new ArrayList<>();
    private List<BookmarkListResponseDto> bookmarkListResponseDtoList = new ArrayList<>();

    @Mock
    private CommonService commonService;

    @Mock
    private BookmarkRepository bookmarkRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private BookmarkServiceImpl bookmarkService;

    @BeforeEach
    public void setUp() {
        // given
        user = new User(USER_ID, EMAIL, NICKNAME, FAVORITE_GENRE, ROLE);
        when(userRepository.save(eq(user))).thenReturn(user);

        recipe1 = new Recipe(ID, RECIPE_ID, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);
        when(recipeRepository.save(eq(recipe1))).thenReturn(recipe1);

        recipe2 = new Recipe(12346L, 2L, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);
        when(recipeRepository.save(eq(recipe2))).thenReturn(recipe2);

        bookmark1 = new Bookmark();
        bookmark1.setUser(user);
        bookmark1.setRecipe(recipe1);
        when(bookmarkRepository.save(eq(bookmark1))).thenReturn(bookmark1);

        bookmark2 = new Bookmark();
        bookmark2.setUser(user);
        bookmark2.setRecipe(recipe2);
    }

    @AfterEach
    public void clean() {
        // clean
        bookmarkRepository.deleteAll();
        recipeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void 북마크추가() {
        // given
        bookmarkRequestDto = new BookmarkRequestDto(user.getId(), recipe2.getRecipeId());

        // when
        when(commonService.findByUserId(user.getId())).thenReturn(user);
        when(commonService.findByRecipeId(recipe2.getRecipeId())).thenReturn(recipe2);
        when(commonService.isBookmarked(user, recipe2)).thenReturn(false);
        when(bookmarkRepository.save(any(Bookmark.class))).thenReturn(bookmark2);

        // then
        assertTrue(bookmarkService.addOrRemoveBookmark(bookmarkRequestDto));
    }

    @Test
    void 북마크삭제() {
        // given
        bookmarkRequestDto = new BookmarkRequestDto(user.getId(), recipe1.getRecipeId());

        // when
        when(commonService.findByUserId(user.getId())).thenReturn(user);
        when(commonService.findByRecipeId(recipe1.getRecipeId())).thenReturn(recipe1);
        when(commonService.isBookmarked(user, recipe1)).thenReturn(false);
        doNothing().when(bookmarkRepository).delete(bookmark1);

        // then
        assertTrue(bookmarkService.addOrRemoveBookmark(bookmarkRequestDto));
    }

    @Test
    void 북마크리스트조회() { /** 수정 필요 **/
        // given
        bookmarkList.add(bookmark1);
        Page<BookmarkListResponseDto> bookmarkListResponseDtoListPage = new PageImpl<>(bookmarkListResponseDtoList, PageRequest.of(PAGE - 1, SIZE), bookmarkList.size());

        // when
        when(commonService.findByUserId(user.getId())).thenReturn(user);
        when(commonService.findBookmarkByUser(user)).thenReturn(bookmarkList);
        when(commonService.getPage(bookmarkListResponseDtoList, PAGE, SIZE)).thenReturn(bookmarkListResponseDtoListPage);

        Page<BookmarkListResponseDto> bookmarkPageResponseDto = bookmarkService.getBookmarkAll(user.getId(), PAGE, SIZE);

        // then
        assertEquals(bookmarkPageResponseDto.getTotalElements(), 1);
    }

    @Test
    void 북마크검색() {
        // given
        bookmarkList.add(bookmark1);
        Page<BookmarkListResponseDto> bookmarkListResponseDtoListPage = new PageImpl<>(bookmarkListResponseDtoList, PageRequest.of(PAGE - 1, SIZE), bookmarkList.size());

        // when
        when(commonService.findByUserId(user.getId())).thenReturn(user);
        when(commonService.findBookmarkByUser(user)).thenReturn(bookmarkList);
        when(commonService.isRecipeMatching(recipe1, TIME.toString(), DIFFICULTY.toString(), COMPOSITION.toString())).thenReturn(true);
        when(commonService.getPage(bookmarkListResponseDtoList, PAGE, SIZE)).thenReturn(bookmarkListResponseDtoListPage);

        Page<BookmarkListResponseDto> bookmarkPageResponseDto = bookmarkService.searchBookmark(user.getId(), PAGE, SIZE, NAME, TIME.getTimeName(), DIFFICULTY.getDifficulty(), COMPOSITION.getComposition());

        // then
        assertEquals(bookmarkPageResponseDto.getTotalElements(), 1);
    }
}