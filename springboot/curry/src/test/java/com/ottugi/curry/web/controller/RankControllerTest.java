package com.ottugi.curry.web.controller;

import com.ottugi.curry.domain.rank.Rank;
import com.ottugi.curry.service.rank.RankService;
import com.ottugi.curry.web.dto.rank.RankResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.ottugi.curry.TestConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RankControllerTest {

    private Rank rank;

    private MockMvc mockMvc;

    @Mock
    private RankService rankService;

    @InjectMocks
    private RankController rankController;

    @BeforeEach
    public void setUp() {
        rank = new Rank(KEYWORD);
        mockMvc = MockMvcBuilders.standaloneSetup(rankController).build();
    }

    @Test
    void 검색어_순위_10개_목록_조회() throws Exception {
        // given
        List<RankResponseDto> rankResponseDtoList = new ArrayList<>();
        rankResponseDtoList.add(new RankResponseDto(rank));
        when(rankService.getRankList()).thenReturn(rankResponseDtoList);

        // then
        mockMvc.perform(get("/api/rank/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(rankResponseDtoList.size())));
    }
}