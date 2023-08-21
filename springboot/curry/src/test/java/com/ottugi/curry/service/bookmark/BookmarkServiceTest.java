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
    private Recipe recipe;
    private Bookmark bookmark;

    private List<Bookmark> bookmarkList = new ArrayList<>();
    private List<BookmarkListResponseDto> bookmarkListResponseDtoList = new ArrayList<>();
    private Page<BookmarkListResponseDto> bookmarkListResponseDtoListPage;

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
        recipe = new Recipe(ID, RECIPE_ID, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setRecipe(recipe);

        bookmarkList.add(bookmark);
        bookmarkListResponseDtoList.add(new BookmarkListResponseDto(recipe, true));
        bookmarkListResponseDtoListPage = new PageImpl<>(bookmarkListResponseDtoList, PageRequest.of(PAGE - 1, SIZE), bookmarkListResponseDtoList.size());
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
        when(commonService.findByUserId(anyLong())).thenReturn(user);
        when(commonService.findByRecipeId(anyLong())).thenReturn(recipe);
        when(commonService.isBookmarked(any(User.class), any(Recipe.class))).thenReturn(false);
        when(bookmarkRepository.save(any(Bookmark.class))).thenReturn(bookmark);

        // when
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto(user.getId(),recipe.getRecipeId());
        Boolean testResponse = bookmarkService.addOrRemoveBookmark(bookmarkRequestDto);

        // then
        assertTrue(testResponse);
    }

    @Test
    void 북마크삭제() {
        // given
        when(commonService.findByUserId(anyLong())).thenReturn(user);
        when(commonService.findByRecipeId(anyLong())).thenReturn(recipe);
        when(bookmarkRepository.save(any(Bookmark.class))).thenReturn(bookmark);
        when(commonService.isBookmarked(any(User.class), any(Recipe.class))).thenReturn(false);
        doNothing().when(bookmarkRepository).delete(bookmark);

        // when
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto(user.getId(), recipe.getRecipeId());
        Boolean testResponse = bookmarkService.addOrRemoveBookmark(bookmarkRequestDto);

        // then
        assertTrue(testResponse);
    }

    @Test
    void 북마크리스트조회() {
        // given
        when(commonService.findByUserId(anyLong())).thenReturn(user);
        when(bookmarkRepository.save(any(Bookmark.class))).thenReturn(bookmark);
        when(commonService.findBookmarkByUser(any(User.class))).thenReturn(bookmarkList);
        doReturn(bookmarkListResponseDtoListPage).when(commonService).getPage(anyList(), anyInt(), anyInt());

        // when
        Page<BookmarkListResponseDto> testBookmarkListPageResponseDto = bookmarkService.getBookmarkAll(user.getId(), PAGE, SIZE);

        // then
        assertEquals(bookmarkListResponseDtoListPage.getTotalElements(), testBookmarkListPageResponseDto.getTotalElements());
    }

    @Test
    void 북마크검색() {
        // given
        when(commonService.findByUserId(anyLong())).thenReturn(user);
        when(bookmarkRepository.save(any(Bookmark.class))).thenReturn(bookmark);
        when(commonService.findBookmarkByUser(any(User.class))).thenReturn(bookmarkList);
        when(commonService.isRecipeMatching(any(Recipe.class), anyString(), anyString(), anyString())).thenReturn(true);
        doReturn(bookmarkListResponseDtoListPage).when(commonService).getPage(anyList(), anyInt(), anyInt());

        // when
        Page<BookmarkListResponseDto> testBookmarkListPageResponseDto = bookmarkService.searchBookmark(eq(user.getId()), PAGE, SIZE, recipe.getName(), recipe.getTime().getTimeName(), recipe.getDifficulty().getDifficulty(), recipe.getComposition().getComposition());

        // then
        assertEquals(bookmarkListResponseDtoListPage.getTotalElements(), testBookmarkListPageResponseDto.getTotalElements());
    }
}