package com.ottugi.curry.service.recipe;

import com.ottugi.curry.domain.bookmark.BookmarkRepository;
import com.ottugi.curry.domain.recipe.*;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.service.lately.LatelyServiceImpl;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeRequestDto;
import com.ottugi.curry.web.dto.recipe.RecipeResponseDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RecipeServiceTest {

    private final Long recipeId1 = 6842324L;
    private final Long recipeId2 = 6848305L;
    private final String name = "고구마맛탕";
    private final String thumbnail = "https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/828bccf4fdd0a71b6477a8e96e84906b1.png";
    private final Time time = Time.ofTime("60분 이내");
    private final Difficulty difficulty = Difficulty.ofDifficulty("초급");
    private final Composition composition = Composition.ofComposition("가볍게");
    private final String ingredients = "[재료] 고구마| 식용유| 황설탕| 올리고당| 견과류| 물";
    private final Servings servings = Servings.ofServings("2인분");
    private final String orders = "|1. 바삭하게 튀기는 꿀팁|2. 달콤한 소스 꿀팁|3. 더 건강하게 먹는 꿀팁";
    private final String photo = "|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/4c9918cf77a109d28b389e6bc753b4bd1.jpg|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/66e8c5f5932e195e7b5405d110a6e67e1.jpg|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/8628264d141fa54487461d41a45d905f1.jpg";

    private final Long userId = 1L;
    private final String email = "wn8925@gmail.com";
    private final String nickName = "가경";

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookmarkRepository bookmarkRepository;

    @Mock
    private LatelyServiceImpl latelyService;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Test
    void 재료로레시피리스트조회() {

        // given
        Recipe recipe1 = new Recipe(recipeId1, name, thumbnail, time, difficulty, composition, ingredients, servings, orders, photo);
        Recipe recipe2 = new Recipe(recipeId2, name, thumbnail, time, difficulty, composition, ingredients, servings, orders, photo);
        User user = new User(userId, email, nickName);

        // when
        List<Recipe> recipeList = Arrays.asList(recipe1, recipe2);
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        when(recipeRepository.findByIngredientsContaining("고구마")).thenReturn(recipeList);
        when(recipeRepository.findByIngredientsContaining("올리고당")).thenReturn(recipeList);
        RecipeRequestDto recipeRequestDto = RecipeRequestDto.builder()
                .userId(userId)
                .ingredients(Arrays.asList("고구마", "올리고당"))
                .page(1)
                .size(10)
                .build();

        Page<RecipeListResponseDto> pagedRecipeList = recipeService.getRecipeList(recipeRequestDto);

        List<RecipeListResponseDto> recipeListResponseDtoList = pagedRecipeList.getContent();

        // then
        assertEquals(recipeListResponseDtoList.size(), 2);
        assertEquals(recipeListResponseDtoList.get(0).getId(), recipeId1);
        assertEquals(recipeListResponseDtoList.get(1).getId(), recipeId2);
    }

    @Test
    void 레시피상세조회() {

        // given
        Recipe recipe = new Recipe(recipeId1, name, thumbnail, time, difficulty, composition, ingredients, servings, orders, photo);
        User user = new User(userId, email, nickName);

        // when
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        when(recipeRepository.findById(recipeId1)).thenReturn(java.util.Optional.of(recipe));
        RecipeResponseDto recipeResponseDto = recipeService.getRecipeDetail(userId, recipeId1);

        // then
        assertNotNull(recipeResponseDto);
        assertEquals(recipeResponseDto.getId(), recipeId1);
    }

    @Test
    void 검색창으로레시피리스트조회() {

        // given
        Recipe recipe1 = new Recipe(recipeId1, name, thumbnail, time, difficulty, composition, ingredients, servings, orders, photo);
        Recipe recipe2 = new Recipe(recipeId2, name, thumbnail, time, difficulty, composition, ingredients, servings, orders, photo);
        User user = new User(userId, email, nickName);

        // when
        List<Recipe> recipeList = Arrays.asList(recipe1, recipe2);
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        when(recipeRepository.findByNameContaining("고구마")).thenReturn(recipeList);

        List<RecipeListResponseDto> recipeListResponseDtoList = recipeService.searchByBox(userId, name, time.toString(), difficulty.toString(), composition.toString());

        // then
        assertEquals(recipeListResponseDtoList.size(), 2);
        assertEquals(recipeListResponseDtoList.get(0).getId(), recipeId1);
        assertEquals(recipeListResponseDtoList.get(1).getId(), recipeId2);
    }
}