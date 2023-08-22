package com.ottugi.curry.web.controller;

import com.ottugi.curry.domain.recipe.*;
import com.ottugi.curry.domain.user.User;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static com.ottugi.curry.TestConstants.*;

@SpringBootTest
@AutoConfigureMockMvc
class RecipeControllerTest {

    private User user;
    private Recipe recipe;

    private final Boolean isBookmark = true;

    private MockMvc mockMvc;

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    @BeforeEach
    public void setUp() {
        user = new User(USER_ID, EMAIL, NICKNAME, FAVORITE_GENRE, ROLE);
        recipe = new Recipe(ID, RECIPE_ID, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    void 레시피상세조회() throws Exception {
        // given
        RecipeResponseDto recipeResponseDto = new RecipeResponseDto(recipe, isBookmark);
        when(recipeService.getRecipeDetail(anyLong(), anyLong())).thenReturn(recipeResponseDto);

        // when, then
        mockMvc.perform(get("/api/recipe")
                    .param("userId", String.valueOf(user.getId()))
                    .param("recipeId", String.valueOf(recipe.getRecipeId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeId").value(recipe.getRecipeId()))
                .andExpect(jsonPath("$.name").value(recipe.getName()))
                .andExpect(jsonPath("$.thumbnail").value(recipe.getThumbnail()))
                .andExpect(jsonPath("$.time").value(recipe.getTime().getTimeName()))
                .andExpect(jsonPath("$.difficulty").value(recipe.getDifficulty().getDifficulty()))
                .andExpect(jsonPath("$.composition").value(recipe.getComposition().getComposition()))
                .andExpect(jsonPath("$.ingredients").value(recipe.getIngredients()))
                .andExpect(jsonPath("$.servings").value(recipe.getServings().getServings()))
                .andExpect(jsonPath("$.orders").value(recipe.getOrders()))
                .andExpect(jsonPath("$.photo").value(recipe.getPhoto()))
                .andExpect(jsonPath("$.isBookmark").value(isBookmark));
    }

    @Test
    void 검색창으로레시피리스트조회() throws Exception {
        // given
        List<RecipeListResponseDto> recipeListResponseDtoList = new ArrayList<>();
        recipeListResponseDtoList.add(new RecipeListResponseDto(recipe, isBookmark));
        Page<RecipeListResponseDto> recipeListResponseDtoPage = new PageImpl<>(recipeListResponseDtoList);
        when(recipeService.searchByBox(anyLong(), anyInt(), anyInt(), anyString(), anyString(), anyString(), anyString())).thenReturn(recipeListResponseDtoPage);

        // when, then
        mockMvc.perform(get("/api/recipe/search")
                        .param("userId", String.valueOf(user.getId()))
                        .param("page", String.valueOf(PAGE))
                        .param("size", String.valueOf(SIZE))
                        .param("name", recipe.getName())
                        .param("time", recipe.getTime().getTimeName())
                        .param("difficulty", recipe.getDifficulty().getDifficulty())
                        .param("composition", recipe.getComposition().getComposition()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(recipeListResponseDtoList.size())));
    }
}