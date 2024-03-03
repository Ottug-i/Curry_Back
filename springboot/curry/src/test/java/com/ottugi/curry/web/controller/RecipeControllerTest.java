package com.ottugi.curry.web.controller;

import static com.ottugi.curry.TestConstants.PAGE;
import static com.ottugi.curry.TestConstants.SIZE;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ottugi.curry.TestObjectFactory;
import com.ottugi.curry.config.SecurityConfig;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.jwt.JwtAuthenticationFilter;
import com.ottugi.curry.service.recipe.RecipeService;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = RecipeController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)})
@WithMockUser
class RecipeControllerTest {
    private User user;
    private Recipe recipe;
    private RecipeResponseDto recipeResponseDto;
    private Page<RecipeListResponseDto> recipeListResponseDtoPage;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    @BeforeEach
    public void setUp() {
        user = TestObjectFactory.initUser();
        recipe = TestObjectFactory.initRecipe();

        recipeResponseDto = TestObjectFactory.initRecipeResponseDto(recipe);
        recipeListResponseDtoPage = TestObjectFactory.initRecipeListResponseDtoPage(recipe);
    }

    @Test
    @DisplayName("레시피 조회 테스트")
    void testRecipeDetails() throws Exception {
        when(recipeService.findRecipeByUserIdAndRecipeId(anyLong(), anyLong())).thenReturn(recipeResponseDto);

        mockMvc.perform(get("/api/recipe")
                        .param("userId", String.valueOf(user.getId()))
                        .param("recipeId", String.valueOf(recipe.getRecipeId()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeId").value(recipeResponseDto.getRecipeId()))
                .andExpect(jsonPath("$.name").value(recipeResponseDto.getName()))
                .andExpect(jsonPath("$.thumbnail").value(recipeResponseDto.getThumbnail()))
                .andExpect(jsonPath("$.time").value(recipeResponseDto.getTime()))
                .andExpect(jsonPath("$.difficulty").value(recipeResponseDto.getDifficulty()))
                .andExpect(jsonPath("$.composition").value(recipeResponseDto.getComposition()))
                .andExpect(jsonPath("$.ingredients").value(recipeResponseDto.getIngredients()))
                .andExpect(jsonPath("$.servings").value(recipeResponseDto.getServings()))
                .andExpect(jsonPath("$.orders").value(recipeResponseDto.getOrders()))
                .andExpect(jsonPath("$.photo").value(recipeResponseDto.getPhoto()))
                .andExpect(jsonPath("$.isBookmark").value(recipeResponseDto.getIsBookmark()));

        verify(recipeService, times(1)).findRecipeByUserIdAndRecipeId(anyLong(), anyLong());
    }

    @Test
    @DisplayName("검색창으로 레시피 조회 테스트")
    void testRecipeSearchOptionPage() throws Exception {
        when(recipeService.findRecipePageBySearchBox(anyLong(), anyInt(), anyInt(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(recipeListResponseDtoPage);

        mockMvc.perform(get("/api/recipe/search")
                        .param("userId", String.valueOf(user.getId()))
                        .param("page", String.valueOf(PAGE))
                        .param("size", String.valueOf(SIZE))
                        .param("name", recipe.getName())
                        .param("time", recipe.getTime().getTimeName())
                        .param("difficulty", recipe.getDifficulty().getDifficulty())
                        .param("composition", recipe.getComposition().getComposition())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(recipeListResponseDtoPage.getSize())));

        verify(recipeService, times(1)).findRecipePageBySearchBox(anyLong(), anyInt(), anyInt(), anyString(), anyString(), anyString(), anyString());
    }
}