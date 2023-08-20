package com.ottugi.curry.service.recipe;

import com.ottugi.curry.domain.recipe.*;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.service.CommonService;
import com.ottugi.curry.service.lately.LatelyService;
import com.ottugi.curry.service.rank.RankService;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

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
    private List<RecipeListResponseDto> recipeListResponseDtoList = new ArrayList<>();

    @Mock
    private CommonService commonService;

    @Mock
    private LatelyService latelyService;

    @Mock
    private RankService rankService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @BeforeEach
    public void setUp() {
        // given
        user = new User(USER_ID, EMAIL, NICKNAME, FAVORITE_GENRE, ROLE);
        when(userRepository.save(eq(user))).thenReturn(user);

        recipe = new Recipe(ID, RECIPE_ID, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);
        when(recipeRepository.save(eq(recipe))).thenReturn(recipe);
    }

    @AfterEach
    public void clean() {
        // clean
        recipeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void 레시피상세조회() {
        // when
        when(commonService.findByUserId(user.getId())).thenReturn(user);
        when(commonService.findByRecipeId(recipe.getRecipeId())).thenReturn(recipe);
        when(latelyService.addLately(user.getId(), recipe.getRecipeId())).thenReturn(true);
        when(commonService.isBookmarked(user, recipe)).thenReturn(true);

        RecipeResponseDto recipeResponseDto = recipeService.getRecipeDetail(user.getId(), recipe.getRecipeId());

        // then
        assertNotNull(recipeResponseDto);
    }

    @Test
    void 레시피검색() {
        // given
        recipeList.add(recipe);
        Page<RecipeListResponseDto> recipeListResponseDtoListPage = new PageImpl<>(recipeListResponseDtoList, PageRequest.of(PAGE - 1, SIZE), 1);

        // when
        when(commonService.findByUserId(user.getId())).thenReturn(new User());
        when(recipeRepository.findByNameContaining(recipe.getName())).thenReturn(recipeList);
        when(commonService.isBookmarked(user, recipe)).thenReturn(true);
        doNothing().when(rankService).updateOrAddRank(recipe.getName());
        when(commonService.getPage(recipeListResponseDtoList, PAGE, SIZE)).thenReturn(recipeListResponseDtoListPage);

        Page<RecipeListResponseDto> recipeListResponseDtoPage = recipeService.searchByBox(user.getId(), PAGE, SIZE, NAME, TIME.getTimeName(), DIFFICULTY.getDifficulty(), COMPOSITION.getComposition());

        // then
        assertEquals(recipeListResponseDtoPage.getTotalElements(), 1);
    }
}