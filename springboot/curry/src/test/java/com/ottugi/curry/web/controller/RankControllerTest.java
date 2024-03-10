package com.ottugi.curry.web.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ottugi.curry.config.SecurityConfig;
import com.ottugi.curry.domain.rank.Rank;
import com.ottugi.curry.domain.rank.RankTest;
import com.ottugi.curry.jwt.JwtAuthenticationFilter;
import com.ottugi.curry.service.rank.RankService;
import com.ottugi.curry.web.dto.rank.RankResponseDto;
import com.ottugi.curry.web.dto.rank.RankResponseDtoTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = RankController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)})
@WithMockUser
public class RankControllerTest {
    private Rank rank;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RankService rankService;

    @BeforeEach
    public void setUp() {
        rank = RankTest.initRank();
    }

    @Test
    @DisplayName("인기 검색어 10개 목록 조회 테스트")
    void testRankList() throws Exception {
        List<RankResponseDto> rankResponseDtoList = RankResponseDtoTest.initRankResponseDtoList(rank);
        when(rankService.findTopRankList()).thenReturn(rankResponseDtoList);

        mockMvc.perform(get("/api/rank/list")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(rankResponseDtoList.size())));

        verify(rankService, times(1)).findTopRankList();
    }
}