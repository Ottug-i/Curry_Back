package com.ottugi.curry.web.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ottugi.curry.TestObjectFactory;
import com.ottugi.curry.config.SecurityConfig;
import com.ottugi.curry.domain.lately.Lately;
import com.ottugi.curry.jwt.JwtAuthenticationFilter;
import com.ottugi.curry.service.lately.LatelyService;
import com.ottugi.curry.web.dto.lately.LatelyListResponseDto;
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

@WebMvcTest(controllers = LatelyController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)})
@WithMockUser
class LatelyControllerTest {
    private Lately lately;
    private List<LatelyListResponseDto> latelyListResponseDtoList;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LatelyService latelyService;

    @BeforeEach
    public void setUp() {
        lately = TestObjectFactory.initLately();
        lately.setUser(TestObjectFactory.initUser());
        lately.setRecipe(TestObjectFactory.initRecipe());

        latelyListResponseDtoList = TestObjectFactory.initLatelyListResponseDtoList(lately);
    }

    @Test
    @DisplayName("최근 본 레시피 목록 조회 테스트")
    void testLatelyList() throws Exception {
        when(latelyService.findLatelyListByUserId(anyLong())).thenReturn(latelyListResponseDtoList);

        mockMvc.perform(get("/api/lately/list")
                        .param("userId", String.valueOf(lately.getUserId().getId()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(latelyListResponseDtoList.size())));
    }

    @Test
    @DisplayName("최근 본 레시피에 따른 3D 모델 캐릭터 조회 테스트")
    void testLatelyGenreFor3DCharacter() throws Exception {
        when(latelyService.findLatelyGenreFor3DCharacter(anyLong())).thenReturn("vegetable");

        mockMvc.perform(get("/api/lately/character")
                        .param("userId", String.valueOf(lately.getUserId().getId()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("vegetable"));
    }
}