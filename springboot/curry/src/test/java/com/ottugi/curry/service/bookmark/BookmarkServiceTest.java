package com.ottugi.curry.service.bookmark;

import static com.ottugi.curry.TestConstants.COMPOSITION;
import static com.ottugi.curry.TestConstants.DIFFICULTY;
import static com.ottugi.curry.TestConstants.EMAIL;
import static com.ottugi.curry.TestConstants.FAVORITE_GENRE;
import static com.ottugi.curry.TestConstants.GENRE;
import static com.ottugi.curry.TestConstants.ID;
import static com.ottugi.curry.TestConstants.INGREDIENTS;
import static com.ottugi.curry.TestConstants.NAME;
import static com.ottugi.curry.TestConstants.NICKNAME;
import static com.ottugi.curry.TestConstants.ORDERS;
import static com.ottugi.curry.TestConstants.PAGE;
import static com.ottugi.curry.TestConstants.PHOTO;
import static com.ottugi.curry.TestConstants.RECIPE_ID;
import static com.ottugi.curry.TestConstants.ROLE;
import static com.ottugi.curry.TestConstants.SERVINGS;
import static com.ottugi.curry.TestConstants.SIZE;
import static com.ottugi.curry.TestConstants.THUMBNAIL;
import static com.ottugi.curry.TestConstants.TIME;
import static com.ottugi.curry.TestConstants.USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.bookmark.BookmarkRepository;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.web.dto.bookmark.BookmarkListResponseDto;
import com.ottugi.curry.web.dto.bookmark.BookmarkRequestDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
class BookmarkServiceTest {

    private User user;
    private Recipe recipe;
    private Bookmark bookmark;

    private final List<Bookmark> bookmarkList = new ArrayList<>();
    private final List<BookmarkListResponseDto> bookmarkListResponseDtoList = new ArrayList<>();
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
        bookmarkListResponseDtoListPage = new PageImpl<>(bookmarkListResponseDtoList, PageRequest.of(PAGE - 1, SIZE),
                bookmarkListResponseDtoList.size());
    }

    @AfterEach
    public void clean() {
        // clean
        bookmarkRepository.deleteAll();
        recipeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void 북마크_추가() {
        // given
        when(commonService.findByUserId(anyLong())).thenReturn(user);
        when(commonService.findByRecipeId(anyLong())).thenReturn(recipe);
        when(commonService.isBookmarked(any(User.class), any(Recipe.class))).thenReturn(false);
        when(bookmarkRepository.save(any(Bookmark.class))).thenReturn(bookmark);

        // when
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto(user.getId(), recipe.getRecipeId());
        Boolean testResponse = bookmarkService.addOrRemoveBookmark(bookmarkRequestDto);

        // then
        assertTrue(testResponse);
    }

    @Test
    void 북마크_삭제() {
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
    void 북마크_목록_조회() {
        // given
        when(commonService.findByUserId(anyLong())).thenReturn(user);
        when(bookmarkRepository.save(any(Bookmark.class))).thenReturn(bookmark);
        when(commonService.findBookmarkByUser(any(User.class))).thenReturn(bookmarkList);
        doReturn(bookmarkListResponseDtoListPage).when(commonService).getPage(anyList(), anyInt(), anyInt());

        // when
        Page<BookmarkListResponseDto> testBookmarkListPageResponseDto = bookmarkService.findBookmarkPageByUserId(user.getId(), PAGE, SIZE);

        // then
        assertEquals(bookmarkListResponseDtoListPage.getTotalElements(), testBookmarkListPageResponseDto.getTotalElements());
    }

    @Test
    void 북마크_레시피_검색() {
        // given
        when(commonService.findByUserId(anyLong())).thenReturn(user);
        when(bookmarkRepository.save(any(Bookmark.class))).thenReturn(bookmark);
        when(commonService.findBookmarkByUser(any(User.class))).thenReturn(bookmarkList);
        when(commonService.isRecipeMatching(any(Recipe.class), anyString(), anyString(), anyString())).thenReturn(true);
        doReturn(bookmarkListResponseDtoListPage).when(commonService).getPage(anyList(), anyInt(), anyInt());

        // when
        Page<BookmarkListResponseDto> testBookmarkListPageResponseDto = bookmarkService.findBookmarkPageByOption(eq(user.getId()), PAGE, SIZE,
                recipe.getName(), recipe.getTime().getTimeName(), recipe.getDifficulty().getDifficulty(), recipe.getComposition().getComposition());

        // then
        assertEquals(bookmarkListResponseDtoListPage.getTotalElements(), testBookmarkListPageResponseDto.getTotalElements());
    }
}