package com.ottugi.curry.service.recipe;

import static com.ottugi.curry.domain.bookmark.BookmarkTest.IS_BOOKMARK;
import static com.ottugi.curry.domain.recipe.RecipeTest.INGREDIENT;
import static com.ottugi.curry.domain.recipe.RecipeTest.PAGE;
import static com.ottugi.curry.domain.recipe.RecipeTest.SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.bookmark.BookmarkTest;
import com.ottugi.curry.domain.recipe.Genre;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.domain.recipe.RecipeTest;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserTest;
import com.ottugi.curry.service.lately.LatelyService;
import com.ottugi.curry.service.rank.RankService;
import com.ottugi.curry.service.user.UserService;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeResponseDto;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {
    private User user;
    private Recipe recipe;

    @Mock
    private UserService userService;
    @Mock
    private LatelyService latelyService;
    @Mock
    private RankService rankService;
    @Mock
    private RecipeRepository recipeRepository;
    @InjectMocks
    private RecipeServiceImpl recipeService;

    @BeforeEach
    public void setUp() {
        user = UserTest.initUser();
        recipe = RecipeTest.initRecipe();
        Bookmark bookmark = BookmarkTest.initBookmark();
        bookmark.setUser(user);
        bookmark.setRecipe(recipe);
        user.addBookmarkList(bookmark);
    }

    @Test
    @DisplayName("회원 아이디와 레시피 아이디로 레시피 조회 테스트")
    void testFindRecipeByUserIdAndRecipeId() {
        when(userService.findUserByUserId(anyLong())).thenReturn(user);
        when(recipeRepository.findByRecipeId(anyLong())).thenReturn(Optional.ofNullable(recipe));
        doNothing().when(latelyService).addLately(any(User.class), any(Recipe.class));

        RecipeResponseDto result = recipeService.findRecipeByUserIdAndRecipeId(user.getId(), recipe.getRecipeId());

        assertRecipeResponseDto(result);

        verify(userService, times(1)).findUserByUserId(anyLong());
        verify(recipeRepository, times(1)).findByRecipeId(anyLong());
        verify(latelyService, times(1)).addLately(any(User.class), any(Recipe.class));
    }

    @Test
    @DisplayName("검색창으로 레시피 조회 테스트")
    void testFindRecipePageBySearchBox() {
        when(userService.findUserByUserId(anyLong())).thenReturn(user);
        when(recipeRepository.findByNameContaining(anyString())).thenReturn(Collections.singletonList(recipe));
        doNothing().when(rankService).updateOrAddRank(anyString());

        Page<RecipeListResponseDto> result = recipeService.findRecipePageBySearchBox(user.getId(), PAGE, SIZE,
                recipe.getName(), recipe.getTime().getTimeName(), recipe.getDifficulty().getDifficultyName(), recipe.getComposition().getCompositionName());

        assertEquals(1, result.getTotalElements());
        assertRecipeListResponseDto(result.getContent().get(0));

        verify(userService, times(1)).findUserByUserId(anyLong());
        verify(recipeRepository, times(1)).findByNameContaining(anyString());
        verify(rankService, times(1)).updateOrAddRank(anyString());
    }

    @Test
    @DisplayName("레시피 아이디로 레시피 조회 테스트")
    void testFindRecipeByRecipeId() {
        when(recipeRepository.findByRecipeId(anyLong())).thenReturn(Optional.ofNullable(recipe));

        Recipe result = recipeService.findRecipeByRecipeId(recipe.getRecipeId());

        assertRecipe(result);

        verify(recipeRepository, times(1)).findByRecipeId(anyLong());
    }

    @Test
    @DisplayName("레시피 아이디 리스트로 레시피 목록 조회 테스트")
    void testFindRecipeListByRecipeIdIn() {
        when(recipeRepository.findByRecipeIdIn(anyList())).thenReturn(Collections.singletonList(recipe));

        List<Recipe> result = recipeService.findRecipeListByRecipeIdIn(Collections.singletonList(recipe.getRecipeId()));

        assertEquals(1, result.size());
        assertRecipe(result.get(0));

        verify(recipeRepository, times(1)).findByRecipeIdIn(anyList());
    }

    @Test
    @DisplayName("레시피 재료로 레시피 목록 조회 테스트")
    void testFindByRecipeListByIngredientsContaining() {
        when(recipeRepository.findByIngredientsContaining(anyString())).thenReturn(Collections.singletonList(recipe));

        List<Recipe> result = recipeService.findByRecipeListByIngredientsContaining(INGREDIENT);

        assertEquals(1, result.size());
        assertRecipe(result.get(0));
        assertTrue(result.get(0).getIngredients().contains(INGREDIENT));

        verify(recipeRepository, times(1)).findByIngredientsContaining(anyString());
    }

    @Test
    @DisplayName("레시피 옵션에 따른 레시피 필터 설정 테스트")
    void testFilterPredicateForOptions() {
        Predicate<Recipe> result = recipeService.filterPredicateForOptions(
                recipe.getTime().getTimeName(), recipe.getDifficulty().getDifficultyName(), recipe.getComposition().getCompositionName());

        assertTrue(result.test(recipe));
    }

    @Test
    @DisplayName("레시피 옵션에 따른 레시피 매칭 설정 시 모든 옵션 제외 테스트")
    void testFilterPredicateForOptionsWithAllBlank() {
        Predicate<Recipe> result = recipeService.filterPredicateForOptions("", "", "");

        assertTrue(result.test(recipe));
    }

    @Test
    @DisplayName("레시피 옵션에 따른 레시피 매칭 설정 테스트")
    void testIsRecipeMatchingCriteria() {
        boolean result = recipeService.isRecipeMatchedCriteria(recipe,
                recipe.getTime().getTimeName(), recipe.getDifficulty().getDifficultyName(), recipe.getComposition().getCompositionName());

        assertTrue(result);
    }

    @Test
    @DisplayName("레시피 옵션에 따른 레시피 매칭 설정 시 시간 옵션 제외 테스트")
    void testIsRecipeMatchingCriteriaWithTimeNull() {
        boolean result = recipeService.isRecipeMatchedCriteria(recipe,
                null, recipe.getDifficulty().getDifficultyName(), recipe.getComposition().getCompositionName());

        assertTrue(result);
    }

    @Test
    @DisplayName("회원과 레시피에 따른 레시피 북마크 여부 조회 테스트")
    void testIsRecipeBookmarked() {
        boolean result = recipeService.isRecipeBookmarked(user, recipe);

        assertTrue(result);
    }

    private void assertRecipe(Recipe resultEntity) {
        assertNotNull(resultEntity);
        assertEquals(recipe.getRecipeId(), resultEntity.getRecipeId());
        assertEquals(recipe.getName(), resultEntity.getName());
        assertEquals(recipe.getThumbnail(), resultEntity.getThumbnail());
        assertEquals(recipe.getTime(), resultEntity.getTime());
        assertEquals(recipe.getDifficulty(), resultEntity.getDifficulty());
        assertEquals(recipe.getComposition(), resultEntity.getComposition());
        assertEquals(recipe.getIngredients(), resultEntity.getIngredients());
        assertEquals(recipe.getServings(), resultEntity.getServings());
        assertEquals(recipe.getOrders(), resultEntity.getOrders());
        assertEquals(recipe.getPhoto(), resultEntity.getPhoto());
    }

    private void assertRecipeResponseDto(RecipeResponseDto resultDto) {
        assertNotNull(resultDto);
        assertEquals(recipe.getRecipeId(), resultDto.getRecipeId());
        assertEquals(recipe.getName(), resultDto.getName());
        assertEquals(recipe.getThumbnail(), resultDto.getThumbnail());
        assertEquals(recipe.getTime().getTimeName(), resultDto.getTime());
        assertEquals(recipe.getDifficulty().getDifficultyName(), resultDto.getDifficulty());
        assertEquals(recipe.getComposition().getCompositionName(), resultDto.getComposition());
        assertEquals(recipe.getIngredients(), resultDto.getIngredients());
        assertEquals(recipe.getServings().getServingName(), resultDto.getServings());
        assertEquals(recipe.getOrders(), resultDto.getOrders());
        assertEquals(recipe.getPhoto(), resultDto.getPhoto());
    }

    private void assertRecipeListResponseDto(RecipeListResponseDto resultDto) {
        assertNotNull(resultDto);
        assertEquals(recipe.getRecipeId(), resultDto.getRecipeId());
        assertEquals(recipe.getName(), resultDto.getName());
        assertEquals(recipe.getThumbnail(), resultDto.getThumbnail());
        assertEquals(recipe.getTime().getTimeName(), resultDto.getTime());
        assertEquals(recipe.getDifficulty().getDifficultyName(), resultDto.getDifficulty());
        assertEquals(recipe.getComposition().getCompositionName(), resultDto.getComposition());
        assertEquals(recipe.getIngredients(), resultDto.getIngredients());
        assertEquals(IS_BOOKMARK, resultDto.getIsBookmark());
        assertEquals(Genre.findMainGenreCharacter(recipe), resultDto.getMainGenre());
    }
}