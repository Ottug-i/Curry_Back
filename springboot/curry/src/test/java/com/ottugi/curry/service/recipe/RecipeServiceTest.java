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

    private Boolean isBookmark = true;

    private List<Recipe> recipeList = new ArrayList<>();
    private List<RecipeListResponseDto> recipeListResponseDtoList = new ArrayList<>();
    private Page<RecipeListResponseDto> recipeListResponseDtoListPage;

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
        when(userRepository.save(any(User.class))).thenReturn(user);

        recipe = new Recipe(ID, RECIPE_ID, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);

        recipeList.add(recipe);
        recipeListResponseDtoList.add(new RecipeListResponseDto(recipe, isBookmark));
        recipeListResponseDtoListPage = new PageImpl<>(recipeListResponseDtoList, PageRequest.of(PAGE - 1, SIZE), recipeListResponseDtoList.size());
    }

    @AfterEach
    public void clean() {
        // clean
        recipeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void 레시피상세조회() {
        // given
        when(commonService.findByUserId(anyLong())).thenReturn(user);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        when(commonService.findByRecipeId(anyLong())).thenReturn(recipe);
        when(latelyService.addLately(anyLong(), anyLong())).thenReturn(true);
        when(commonService.isBookmarked(any(User.class), any(Recipe.class))).thenReturn(true);

        // when
        RecipeResponseDto testRecipeResponseDto = recipeService.getRecipeDetail(user.getId(), recipe.getRecipeId());

        // then
        assertNotNull(testRecipeResponseDto);
        assertEquals(recipe.getRecipeId(), testRecipeResponseDto.getRecipeId());
        assertEquals(recipe.getName(), testRecipeResponseDto.getName());
        assertEquals(recipe.getThumbnail(), testRecipeResponseDto.getThumbnail());
        assertEquals(recipe.getTime().getTimeName(), testRecipeResponseDto.getTime());
        assertEquals(recipe.getDifficulty().getDifficulty(), testRecipeResponseDto.getDifficulty());
        assertEquals(recipe.getComposition().getComposition(), testRecipeResponseDto.getComposition());
        assertEquals(recipe.getIngredients(), testRecipeResponseDto.getIngredients());
        assertEquals(recipe.getServings().getServings(), testRecipeResponseDto.getServings());
        assertEquals(recipe.getOrders(), testRecipeResponseDto.getOrders());
        assertEquals(recipe.getPhoto(), testRecipeResponseDto.getPhoto());
        assertEquals(isBookmark, testRecipeResponseDto.getIsBookmark());
    }

    @Test
    void 레시피검색() {
        // given
        when(commonService.findByUserId(anyLong())).thenReturn(user);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        when(recipeRepository.findByNameContaining(anyString())).thenReturn(recipeList);
        when(commonService.isBookmarked(any(User.class), any(Recipe.class))).thenReturn(true);
        doNothing().when(rankService).updateOrAddRank(anyString());
        doReturn(recipeListResponseDtoListPage).when(commonService).getPage(anyList(), anyInt(), anyInt());

        // when
        Page<RecipeListResponseDto> testRecipeListPageResponseDto = recipeService.searchByBox(user.getId(), PAGE, SIZE, NAME, TIME.getTimeName(), DIFFICULTY.getDifficulty(), COMPOSITION.getComposition());

        // then
        assertEquals(recipeListResponseDtoListPage.getTotalElements(), testRecipeListPageResponseDto.getTotalElements());
    }
}