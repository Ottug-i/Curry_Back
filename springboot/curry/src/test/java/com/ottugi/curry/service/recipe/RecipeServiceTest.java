package com.ottugi.curry.service.recipe;

import com.ottugi.curry.domain.recipe.*;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.service.CommonService;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RecipeServiceTest {

    private User user;
    private Recipe recipe;
    private List<Recipe> recipeList = new ArrayList<>();

    @Mock
    private CommonService commonService;

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @BeforeEach
    public void setUp() {
        // given
        user = new User();
        recipe = new Recipe();
    }

    @Test
    void 레시피상세조회() {
        // when
        when(commonService.findByUserId(USER_ID)).thenReturn(user);
        when(commonService.findByRecipeId(RECIPE_ID)).thenReturn(recipe);
        when(commonService.isBookmarked(user, recipe)).thenReturn(true);

        RecipeResponseDto response = recipeService.getRecipeDetail(anyLong(), anyLong());

        // then
        assertNotNull(response);
    }

    @Test
    void 레시피검색() {
        // when
        when(commonService.findByUserId(USER_ID)).thenReturn(new User());
        when(recipeRepository.findByNameContaining(NAME)).thenReturn(recipeList);
        when(commonService.isRecipeMatching(recipe, TIME.getTimeName(), DIFFICULTY.getDifficulty(), COMPOSITION.getComposition())).thenReturn(true);

        Page<RecipeListResponseDto> response = recipeService.searchByBox(USER_ID, PAGE, SIZE, NAME, TIME.getTimeName(), DIFFICULTY.getDifficulty(), COMPOSITION.getComposition());

        // then
        assertEquals(response.getTotalElements(), 0);
    }
}