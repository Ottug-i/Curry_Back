package com.ottugi.curry.service.recipe;

import static com.ottugi.curry.TestConstants.COMPOSITION;
import static com.ottugi.curry.TestConstants.DIFFICULTY;
import static com.ottugi.curry.TestConstants.EMAIL;
import static com.ottugi.curry.TestConstants.FAVORITE_GENRE;
import static com.ottugi.curry.TestConstants.GENRE;
import static com.ottugi.curry.TestConstants.ID;
import static com.ottugi.curry.TestConstants.INGREDIENTS;
import static com.ottugi.curry.TestConstants.NAME;
import static com.ottugi.curry.TestConstants.NICKNAME;
import static com.ottugi.curry.TestConstants.ORDERS;
import static com.ottugi.curry.TestConstants.PAGE;
import static com.ottugi.curry.TestConstants.PHOTO;
import static com.ottugi.curry.TestConstants.RECIPE_ID;
import static com.ottugi.curry.TestConstants.ROLE;
import static com.ottugi.curry.TestConstants.SERVINGS;
import static com.ottugi.curry.TestConstants.SIZE;
import static com.ottugi.curry.TestConstants.THUMBNAIL;
import static com.ottugi.curry.TestConstants.TIME;
import static com.ottugi.curry.TestConstants.USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.service.lately.LatelyService;
import com.ottugi.curry.service.rank.RankService;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeResponseDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
class RecipeServiceTest {

    private User user;
    private Recipe recipe;

    private final Boolean isBookmark = true;

    private final List<Recipe> recipeList = new ArrayList<>();
    private final List<RecipeListResponseDto> recipeListResponseDtoList = new ArrayList<>();
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
    void 레시피_조회() {
        // given
        when(commonService.findByUserId(anyLong())).thenReturn(user);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        when(commonService.findByRecipeId(anyLong())).thenReturn(recipe);
        when(latelyService.addLately(anyLong(), anyLong())).thenReturn(true);
        when(commonService.isBookmarked(any(User.class), any(Recipe.class))).thenReturn(true);

        // when
        RecipeResponseDto testRecipeResponseDto = recipeService.findRecipeByUserIdAndRecipeId(user.getId(), recipe.getRecipeId());

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
    void 레시피_검색() {
        // given
        when(commonService.findByUserId(anyLong())).thenReturn(user);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        when(recipeRepository.findByNameContaining(anyString())).thenReturn(recipeList);
        when(commonService.isBookmarked(any(User.class), any(Recipe.class))).thenReturn(true);
        doNothing().when(rankService).updateOrAddRank(anyString());
        doReturn(recipeListResponseDtoListPage).when(commonService).getPage(anyList(), anyInt(), anyInt());

        // when
        Page<RecipeListResponseDto> testRecipeListPageResponseDto = recipeService.findRecipePageBySearchBox(user.getId(), PAGE, SIZE, NAME,
                TIME.getTimeName(),
                DIFFICULTY.getDifficulty(), COMPOSITION.getComposition());

        // then
        assertEquals(recipeListResponseDtoListPage.getTotalElements(), testRecipeListPageResponseDto.getTotalElements());
    }
}