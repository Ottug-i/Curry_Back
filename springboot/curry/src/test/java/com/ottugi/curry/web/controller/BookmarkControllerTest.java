package com.ottugi.curry.web.controller;

import static com.ottugi.curry.TestConstants.PAGE;
import static com.ottugi.curry.TestConstants.SIZE;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ottugi.curry.TestObjectFactory;
import com.ottugi.curry.config.SecurityConfig;
import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.jwt.JwtAuthenticationFilter;
import com.ottugi.curry.service.bookmark.BookmarkService;
import com.ottugi.curry.web.dto.bookmark.BookmarkListResponseDto;
import com.ottugi.curry.web.dto.bookmark.BookmarkRequestDto;
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
    private BookmarkRequestDto bookmarkRequestDto;
    private Page<BookmarkListResponseDto> bookmarkListResponseDtoPage;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookmarkService bookmarkService;

    @BeforeEach
    public void setUp() {
        bookmark = TestObjectFactory.initBookmark();
        bookmark.setUser(TestObjectFactory.initUser());
        bookmark.setRecipe(TestObjectFactory.initRecipe());

        bookmarkRequestDto = TestObjectFactory.initBookmarkRequestDto(bookmark);
        bookmarkListResponseDtoPage = TestObjectFactory.initBookmarkListResponseDtoPage(bookmark);
    }

    @Test
    @DisplayName("북마크 추가 테스트")
    void testBookmarkAdd() throws Exception {
        when(bookmarkService.addOrRemoveBookmark(any(BookmarkRequestDto.class))).thenReturn(true);

        mockMvc.perform(post("/api/bookmark")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookmarkRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @DisplayName("북마크 삭제 테스트")
    void testBookmarkRemove() throws Exception {
        when(bookmarkService.addOrRemoveBookmark(any(BookmarkRequestDto.class))).thenReturn(false);

        mockMvc.perform(post("/api/bookmark")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookmarkRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    @DisplayName("북마크 목록 페이지 조회 테스트")
    void testBookmarkPage() throws Exception {
        when(bookmarkService.findBookmarkPageByUserId(anyLong(), anyInt(), anyInt())).thenReturn(bookmarkListResponseDtoPage);

        mockMvc.perform(get("/api/bookmark/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", String.valueOf(bookmark.getUserId().getId()))
                        .param("page", String.valueOf(PAGE))
                        .param("size", String.valueOf(SIZE))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(bookmarkListResponseDtoPage.getSize())));
    }

    @Test
    @DisplayName("옵션에 따른 북마크 목록 페이지 조회 테스트")
    void testBookmarkSearchOptionPage() throws Exception {
        when(bookmarkService.findBookmarkPageByOption(anyLong(), anyInt(), anyInt(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(bookmarkListResponseDtoPage);

        mockMvc.perform(get("/api/bookmark/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", String.valueOf(bookmark.getUserId().getId()))
                        .param("page", String.valueOf(PAGE))
                        .param("size", String.valueOf(SIZE))
                        .param("name", bookmark.getRecipeId().getName())
                        .param("time", bookmark.getRecipeId().getTime().getTimeName())
                        .param("difficulty", bookmark.getRecipeId().getDifficulty().getDifficulty())
                        .param("composition", bookmark.getRecipeId().getComposition().getComposition())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(bookmarkListResponseDtoPage.getSize())));
    }
}