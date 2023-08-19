package com.ottugi.curry.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ottugi.curry.service.bookmark.BookmarkService;
import com.ottugi.curry.web.dto.bookmark.BookmarkListResponseDto;
import com.ottugi.curry.web.dto.bookmark.BookmarkRequestDto;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static com.ottugi.curry.TestConstants.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookmarkControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookmarkService bookmarkService;

    @InjectMocks
    private BookmarkController bookmarkController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookmarkController).build();
    }

    @Test
    void 북마크추가및삭제() throws Exception {
        // given
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto(USER_ID, RECIPE_ID);

        // when
        when(bookmarkService.addOrRemoveBookmark(bookmarkRequestDto)).thenReturn(true);

        // then
        mockMvc.perform(post("/api/bookmark")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookmarkRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        // when
        when(bookmarkService.addOrRemoveBookmark(bookmarkRequestDto)).thenReturn(false);

        // then
        mockMvc.perform(post("/api/bookmark")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookmarkRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void 북마크리스트조회() throws Exception {
        // given
        List<BookmarkListResponseDto> bookmarkListResponseDtoList = new ArrayList<>();
        Page<BookmarkListResponseDto> bookmarkListResponseDtoPage = new PageImpl<>(bookmarkListResponseDtoList);

        // when
        when(bookmarkService.getBookmarkAll(USER_ID, PAGE, SIZE)).thenReturn(bookmarkListResponseDtoPage);

        // then
        mockMvc.perform(get("/api/bookmark/list")
                        .param("userId", String.valueOf(USER_ID))
                        .param("page", String.valueOf(PAGE))
                        .param("size", String.valueOf(SIZE))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }

    @Test
    void 북마크검색() throws Exception {
        // given
        List<BookmarkListResponseDto> bookmarkListResponseDtoList = new ArrayList<>();
        Page<BookmarkListResponseDto> bookmarkListResponseDtoPage = new PageImpl<>(bookmarkListResponseDtoList);

        // when
        when(bookmarkService.searchBookmark(USER_ID, PAGE, SIZE, NAME, TIME.getTimeName(), DIFFICULTY.getDifficulty(), COMPOSITION.getComposition())).thenReturn(bookmarkListResponseDtoPage);

        // then
        mockMvc.perform(get("/api/bookmark/search")
                        .param("userId", String.valueOf(USER_ID))
                        .param("page", String.valueOf(PAGE))
                        .param("size", String.valueOf(SIZE))
                        .param("name", NAME)
                        .param("time", TIME.getTimeName())
                        .param("difficulty", DIFFICULTY.getDifficulty())
                        .param("composition", COMPOSITION.getComposition())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }
}