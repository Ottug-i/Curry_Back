package com.ottugi.curry.web.controller;

import com.ottugi.curry.domain.recipe.*;
import com.ottugi.curry.service.recipe.RecipeService;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
class RecipeControllerTest {

    private Recipe recipe1;
    private Recipe recipe2;

    private Long recipeId1 = 1234L;
    private Long recipeId2 = 1235L;

    private final Boolean isBookmark = true;

    private MockMvc mockMvc;

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    @BeforeEach
    public void setUp() {
        recipe1 = new Recipe(ID, recipeId1, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);
        recipe2 = new Recipe(12346L, recipeId2, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    void 레시피상세조회() throws Exception {
        // given
        RecipeResponseDto recipeResponseDto = new RecipeResponseDto(recipe1, isBookmark);

        // when
        when(recipeService.getRecipeDetail(USER_ID, recipeId1)).thenReturn(recipeResponseDto);

        // then
        mockMvc.perform(get("/api/recipe")
                    .param("userId", String.valueOf(USER_ID))
                    .param("recipeId", String.valueOf(recipeId1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeId").value(recipeId1))
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.thumbnail").value(THUMBNAIL))
                .andExpect(jsonPath("$.time").value(TIME))
                .andExpect(jsonPath("$.difficulty").value(DIFFICULTY))
                .andExpect(jsonPath("$.composition").value(COMPOSITION))
                .andExpect(jsonPath("$.ingredients").value(INGREDIENTS))
                .andExpect(jsonPath("$.servings").value(SERVINGS))
                .andExpect(jsonPath("$.orders").value(ORDERS))
                .andExpect(jsonPath("$.photo").value(PHOTO))
                .andExpect(jsonPath("$.isBookmark").value(isBookmark));
    }

    @Test
    void 검색창으로레시피리스트조회() throws Exception {
        // given
        List<RecipeListResponseDto> recipeListResponseDtoList = new ArrayList<>();
        recipeListResponseDtoList.add(new RecipeListResponseDto(recipe1, isBookmark));
        recipeListResponseDtoList.add(new RecipeListResponseDto(recipe2, isBookmark));

        Page<RecipeListResponseDto> recipeListResponseDtoPage = new PageImpl<>(recipeListResponseDtoList);

        // when
        when(recipeService.searchByBox(USER_ID, PAGE, SIZE, NAME, TIME.getTimeName(), DIFFICULTY.getDifficulty(), COMPOSITION.getComposition())).thenReturn(recipeListResponseDtoPage);

        // then
        mockMvc.perform(get("/api/recipe/searchByBox")
                        .param("userId", String.valueOf(USER_ID))
                        .param("page", String.valueOf(PAGE))
                        .param("size", String.valueOf(SIZE))
                        .param("name", NAME)
                        .param("time", TIME.getTimeName())
                        .param("difficulty", DIFFICULTY.getDifficulty())
                        .param("composition", COMPOSITION.getComposition()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(recipeListResponseDtoList.size())));
    }
}