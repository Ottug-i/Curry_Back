package com.ottugi.curry.web.controller;

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
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.service.bookmark.BookmarkService;
import com.ottugi.curry.web.dto.bookmark.BookmarkListResponseDto;
import com.ottugi.curry.web.dto.bookmark.BookmarkRequestDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class BookmarkControllerTest {

    private User user;
    private Recipe recipe;
    private Bookmark bookmark;

    private final Boolean isBookmark = true;

    private MockMvc mockMvc;

    @Mock
    private BookmarkService bookmarkService;

    @InjectMocks
    private BookmarkController bookmarkController;

    @BeforeEach
    public void setUp() {
        user = new User(USER_ID, EMAIL, NICKNAME, FAVORITE_GENRE, ROLE);
        recipe = new Recipe(ID, RECIPE_ID, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);
        bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setRecipe(recipe);
        mockMvc = MockMvcBuilders.standaloneSetup(bookmarkController).build();
    }

    @Test
    void 북마크_추가_및_삭제() throws Exception {
        // given
        when(bookmarkService.addOrRemoveBookmark(any(BookmarkRequestDto.class))).thenReturn(true);

        // when, then
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto(user.getId(), recipe.getRecipeId());
        mockMvc.perform(post("/api/bookmark")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookmarkRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        // given
        when(bookmarkService.addOrRemoveBookmark(any(BookmarkRequestDto.class))).thenReturn(false);

        // when, then
        mockMvc.perform(post("/api/bookmark")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookmarkRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void 북마크_목록_조회() throws Exception {
        // given
        List<BookmarkListResponseDto> bookmarkListResponseDtoList = new ArrayList<>();
        bookmarkListResponseDtoList.add(new BookmarkListResponseDto(bookmark.getRecipeId(), isBookmark));
        Page<BookmarkListResponseDto> bookmarkListResponseDtoPage = new PageImpl<>(bookmarkListResponseDtoList);
        when(bookmarkService.findBookmarkPageByUserId(anyLong(), anyInt(), anyInt())).thenReturn(bookmarkListResponseDtoPage);

        // when, then
        mockMvc.perform(get("/api/bookmark/list")
                        .param("userId", String.valueOf(user.getId()))
                        .param("page", String.valueOf(PAGE))
                        .param("size", String.valueOf(SIZE))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(bookmarkListResponseDtoPage.getSize())));
    }

    @Test
    void 북마크_레시피_검색() throws Exception {
        // given
        List<BookmarkListResponseDto> bookmarkListResponseDtoList = new ArrayList<>();
        bookmarkListResponseDtoList.add(new BookmarkListResponseDto(bookmark.getRecipeId(), isBookmark));
        Page<BookmarkListResponseDto> bookmarkListResponseDtoPage = new PageImpl<>(bookmarkListResponseDtoList);
        when(bookmarkService.findBookmarkPageByOption(anyLong(), anyInt(), anyInt(), anyString(), anyString(), anyString(), anyString())).thenReturn(
                bookmarkListResponseDtoPage);

        // when, then
        mockMvc.perform(get("/api/bookmark/search")
                        .param("userId", String.valueOf(user.getId()))
                        .param("page", String.valueOf(PAGE))
                        .param("size", String.valueOf(SIZE))
                        .param("name", recipe.getName())
                        .param("time", recipe.getTime().getTimeName())
                        .param("difficulty", recipe.getDifficulty().getDifficulty())
                        .param("composition", recipe.getComposition().getComposition())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(bookmarkListResponseDtoPage.getSize())));
    }
}