package com.ottugi.curry.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ottugi.curry.domain.recipe.Composition;
import com.ottugi.curry.domain.recipe.Difficulty;
import com.ottugi.curry.domain.recipe.Time;
import com.ottugi.curry.service.bookmark.BookmarkServiceImpl;
import com.ottugi.curry.web.dto.bookmark.BookmarkListResponseDto;
import com.ottugi.curry.web.dto.bookmark.BookmarkRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookmarkControllerTest {

    private final Long userId = 1L;

    private final Long recipeId = 1234L;
    private final String name = "고구마맛탕";
    private final String time = "60분 이내";
    private final String difficulty = "초급";
    private final String composition = "가볍게";

    private MockMvc mockMvc;

    @Mock
    private BookmarkServiceImpl bookmarkService;

    @InjectMocks
    private BookmarkController bookmarkController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookmarkController).build();
    }

    @Test
    void 북마크추가및삭제() throws Exception {

        // given
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto(userId, recipeId);

        // when
        when(bookmarkService.addOrRemoveBookmark(any(BookmarkRequestDto.class))).thenReturn(true);

        // then
        mockMvc.perform(post("/api/bookmark/addAndRemoveBookmark")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookmarkRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        // when
        when(bookmarkService.addOrRemoveBookmark(any(BookmarkRequestDto.class))).thenReturn(false);

        // then
        mockMvc.perform(post("/api/bookmark/addAndRemoveBookmark")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookmarkRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void 북마크리스트조회() throws Exception {

        // given
        List<BookmarkListResponseDto> bookmarkListResponseDtoList = new ArrayList<>();

        // when
        when(bookmarkService.getBookmarkAll(userId)).thenReturn(bookmarkListResponseDtoList);

        // then
        mockMvc.perform(get("/api/bookmark/getBookmarkAll")
                        .param("userId", String.valueOf(userId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void 이름으로북마크조회() throws Exception {

        // given
        List<BookmarkListResponseDto> bookmarkListResponseDtoList = new ArrayList<>();

        // when
        when(bookmarkService.searchByName(userId, name)).thenReturn(bookmarkListResponseDtoList);

        // then
        mockMvc.perform(get("/api/bookmark/searchByName")
                        .param("userId", String.valueOf(userId))
                        .param("name", name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void 옵션으로북마크조회() throws Exception {

        // given
        List<BookmarkListResponseDto> bookmarkListResponseDtoList = new ArrayList<>();

        // when
        when(bookmarkService.searchByOption(userId, time, difficulty, composition)).thenReturn(bookmarkListResponseDtoList);

        // then
        mockMvc.perform(get("/api/bookmark/searchByOption")
                        .param("userId", String.valueOf(userId))
                        .param("time", time)
                        .param("difficulty", difficulty)
                        .param("composition", composition)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}