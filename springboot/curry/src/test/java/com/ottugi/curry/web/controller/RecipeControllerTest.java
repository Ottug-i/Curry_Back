package com.ottugi.curry.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ottugi.curry.domain.recipe.*;
import com.ottugi.curry.service.recipe.RecipeServiceImpl;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeRequestDto;
import com.ottugi.curry.web.dto.recipe.RecipeResponseDto;
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
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RecipeControllerTest {

    private final Long recipeId1 = 1234L;
    private final Long recipeId2 = 1235L;
    private final String name = "고구마맛탕";
    private final String thumbnail = "https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/828bccf4fdd0a71b6477a8e96e84906b1.png";
    private final Time time = Time.ofTime("60분 이내");
    private final Difficulty difficulty = Difficulty.ofDifficulty("초급");
    private final Composition composition = Composition.ofComposition("가볍게");
    private final String ingredients = "[재료] 고구마| 식용유| 황설탕| 올리고당| 견과류| 물";
    private final Servings servings = Servings.ofServings("2인분");
    private final String orders = "|1. 바삭하게 튀기는 꿀팁|2. 달콤한 소스 꿀팁|3. 더 건강하게 먹는 꿀팁";
    private final String photo = "|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/4c9918cf77a109d28b389e6bc753b4bd1.jpg|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/66e8c5f5932e195e7b5405d110a6e67e1.jpg|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/8628264d141fa54487461d41a45d905f1.jpg";

    private final Boolean isBookmark = true;

    private final Long userId = 1L;

    private final int page = 1;
    private final int size = 10;

    private MockMvc mockMvc;

    @Mock
    private RecipeServiceImpl recipeService;

    @InjectMocks
    private RecipeController recipeController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    void 재료로레시피리스트조회() throws Exception {

        // given
        RecipeRequestDto recipeRequestDto = RecipeRequestDto.builder()
                .userId(userId)
                .ingredients(Arrays.asList("고구마", "올리고당"))
                .page(1)
                .size(10)
                .build();

        Recipe recipe1 = new Recipe(recipeId1, name, thumbnail, time, difficulty, composition, ingredients, servings, orders, photo);
        Recipe recipe2 = new Recipe(recipeId2, name, thumbnail, time, difficulty, composition, ingredients, servings, orders, photo);

        List<RecipeListResponseDto> recipeListResponseDtoList = new ArrayList<>();
        recipeListResponseDtoList.add(new RecipeListResponseDto(recipe1, isBookmark));
        recipeListResponseDtoList.add(new RecipeListResponseDto(recipe2, isBookmark));

        Page<RecipeListResponseDto> pagedRecipeList = new PageImpl<>(recipeListResponseDtoList);

        // when
        when(recipeService.getRecipeList(recipeRequestDto)).thenReturn(pagedRecipeList);

        // then
        mockMvc.perform(post("/api/recipe/getRecipeList")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(recipeRequestDto)))
                .andExpect(status().isOk());
    }

    @Test
    void 레시피상세조회() throws Exception {

        // given
        Recipe recipe = new Recipe(recipeId1, name, thumbnail, time, difficulty, composition, ingredients, servings, orders, photo);
        RecipeResponseDto recipeResponseDto = new RecipeResponseDto(recipe, isBookmark);

        // when
        when(recipeService.getRecipeDetail(userId, recipeId1)).thenReturn(recipeResponseDto);

        // then
        mockMvc.perform(get("/api/recipe/getRecipeDetail")
                    .param("userId", String.valueOf(userId))
                    .param("recipeId", String.valueOf(recipeId1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeId").value(recipeId1));
    }

    @Test
    void 검색창으로레시피리스트조회() throws Exception {

        // given
        RecipeRequestDto recipeRequestDto = RecipeRequestDto.builder()
                .userId(userId)
                .ingredients(Arrays.asList("고구마", "올리고당"))
                .page(1)
                .size(10)
                .build();

        Recipe recipe1 = new Recipe(recipeId1, name, thumbnail, time, difficulty, composition, ingredients, servings, orders, photo);
        Recipe recipe2 = new Recipe(recipeId2, name, thumbnail, time, difficulty, composition, ingredients, servings, orders, photo);

        List<RecipeListResponseDto> recipeListResponseDtoList = new ArrayList<>();
        recipeListResponseDtoList.add(new RecipeListResponseDto(recipe1, isBookmark));
        recipeListResponseDtoList.add(new RecipeListResponseDto(recipe2, isBookmark));

        Page<RecipeListResponseDto> recipeListResponseDtoPage = new PageImpl<>(recipeListResponseDtoList);

        // when
        when(recipeService.searchByBox(userId, page, size, name, time.toString(), difficulty.toString(), composition.toString())).thenReturn(recipeListResponseDtoPage);

        // then
        mockMvc.perform(get("/api/recipe/searchByBox")
                        .param("userId", String.valueOf(userId))
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("name", name)
                        .param("time", time.toString())
                        .param("difficulty", difficulty.toString())
                        .param("composition", composition.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(recipeListResponseDtoList.size())));
    }
}