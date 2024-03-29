package com.ottugi.curry.web.controller;

import static com.ottugi.curry.domain.recipe.RecipeTest.PAGE;
import static com.ottugi.curry.domain.recipe.RecipeTest.SIZE;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ottugi.curry.config.SecurityConfig;
import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.bookmark.BookmarkTest;
import com.ottugi.curry.domain.recipe.RecipeTest;
import com.ottugi.curry.domain.user.UserTest;
import com.ottugi.curry.jwt.JwtAuthenticationFilter;
import com.ottugi.curry.service.bookmark.BookmarkService;
import com.ottugi.curry.web.dto.bookmark.BookmarkListResponseDto;
import com.ottugi.curry.web.dto.bookmark.BookmarkListResponseDtoTest;
import com.ottugi.curry.web.dto.bookmark.BookmarkRequestDto;
import com.ottugi.curry.web.dto.bookmark.BookmarkRequestDtoTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = BookmarkController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)})
@WithMockUser
class BookmarkControllerTest {
    private Bookmark bookmark;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookmarkService bookmarkService;

    @BeforeEach
    public void setUp() {
        bookmark = BookmarkTest.initBookmark();
        bookmark.setUser(UserTest.initUser());
        bookmark.setRecipe(RecipeTest.initRecipe());
    }

    @Test
    @DisplayName("북마크 추가 테스트")
    void testBookmarkAdd() throws Exception {
        when(bookmarkService.addOrRemoveBookmark(any(BookmarkRequestDto.class))).thenReturn(true);

        BookmarkRequestDto bookmarkRequestDto = BookmarkRequestDtoTest.initBookmarkRequestDto(bookmark);
        mockMvc.perform(post("/api/bookmark")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookmarkRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(bookmarkService, times(1)).addOrRemoveBookmark(any(BookmarkRequestDto.class));
    }

    @Test
    @DisplayName("북마크 삭제 테스트")
    void testBookmarkRemove() throws Exception {
        when(bookmarkService.addOrRemoveBookmark(any(BookmarkRequestDto.class))).thenReturn(false);

        BookmarkRequestDto bookmarkRequestDto = BookmarkRequestDtoTest.initBookmarkRequestDto(bookmark);
        mockMvc.perform(post("/api/bookmark")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookmarkRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));

        verify(bookmarkService, times(1)).addOrRemoveBookmark(any(BookmarkRequestDto.class));
    }

    @Test
    @DisplayName("북마크 목록 페이지 조회 테스트")
    void testBookmarkPage() throws Exception {
        Page<BookmarkListResponseDto> bookmarkListResponseDtoPage = BookmarkListResponseDtoTest.initBookmarkListResponseDtoPage(bookmark);
        when(bookmarkService.findBookmarkPageByUserId(anyLong(), anyInt(), anyInt())).thenReturn(bookmarkListResponseDtoPage);

        mockMvc.perform(get("/api/bookmark/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", String.valueOf(bookmark.getUserId().getId()))
                        .param("page", String.valueOf(PAGE))
                        .param("size", String.valueOf(SIZE))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(bookmarkListResponseDtoPage.getSize())));

        verify(bookmarkService, times(1)).findBookmarkPageByUserId(anyLong(), anyInt(), anyInt());
    }

    @Test
    @DisplayName("옵션에 따른 북마크 목록 페이지 조회 테스트")
    void testBookmarkSearchOptionPage() throws Exception {
        Page<BookmarkListResponseDto> bookmarkListResponseDtoPage = BookmarkListResponseDtoTest.initBookmarkListResponseDtoPage(bookmark);
        when(bookmarkService.findBookmarkPageByOption(anyLong(), anyInt(), anyInt(), anyString(), anyString(), anyString(), anyString())).thenReturn(bookmarkListResponseDtoPage);

        mockMvc.perform(get("/api/bookmark/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", String.valueOf(bookmark.getUserId().getId()))
                        .param("page", String.valueOf(PAGE))
                        .param("size", String.valueOf(SIZE))
                        .param("name", bookmark.getRecipeId().getName())
                        .param("time", bookmark.getRecipeId().getTime().getTimeName())
                        .param("difficulty", bookmark.getRecipeId().getDifficulty().getDifficultyName())
                        .param("composition", bookmark.getRecipeId().getComposition().getCompositionName())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(bookmarkListResponseDtoPage.getSize())));

        verify(bookmarkService, times(1)).findBookmarkPageByOption(anyLong(), anyInt(), anyInt(), anyString(), anyString(), anyString(), anyString());
    }
}