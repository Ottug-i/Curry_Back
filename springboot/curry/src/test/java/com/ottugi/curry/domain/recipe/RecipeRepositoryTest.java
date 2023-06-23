package com.ottugi.curry.domain.recipe;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class RecipeRepositoryTest {

    private final Long recipeId = 6842324L;
    private final String name = "고구마맛탕";
    private final String thumbnail = "https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/828bccf4fdd0a71b6477a8e96e84906b1.png";
    private final Time time = Time.ofTime("60분 이내");
    private final Difficulty difficulty = Difficulty.ofDifficulty("초급");
    private final Composition composition = Composition.ofComposition("가볍게");
    private final String ingredients = "[재료] 고구마| 식용유| 황설탕| 올리고당| 견과류| 물";
    private final Servings servings = Servings.ofServings("2인분");
    private final String orders = "|1. 바삭하게 튀기는 꿀팁|2. 달콤한 소스 꿀팁|3. 더 건강하게 먹는 꿀팁";
    private final String photo = "|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/4c9918cf77a109d28b389e6bc753b4bd1.jpg|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/66e8c5f5932e195e7b5405d110a6e67e1.jpg|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/8628264d141fa54487461d41a45d905f1.jpg";

    private Recipe recipe;

    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    public void setUp() {

        // given
        Recipe recipe = Recipe.builder()
                .id(recipeId)
                .name(name)
                .thumbnail(thumbnail)
                .time(time)
                .difficulty(difficulty)
                .composition(composition)
                .ingredients(ingredients)
                .servings(servings)
                .orders(orders)
                .photo(photo)
                .build();
        recipeRepository.save(recipe);
    }

    @AfterEach
    void clean() {
        recipeRepository.deleteAll();
    }

    @Test
    void 레시피추가() {

        // given
        Recipe newRecipe = Recipe.builder()
                .id(recipeId)
                .name(name)
                .thumbnail(thumbnail)
                .time(time)
                .difficulty(difficulty)
                .composition(composition)
                .ingredients(ingredients)
                .servings(servings)
                .orders(orders)
                .photo(photo)
                .build();

        // when
        Recipe addRecipe = recipeRepository.save(newRecipe);

        // then
        assertEquals(addRecipe.getName(), name);
        assertEquals(addRecipe.getThumbnail(), thumbnail);
        assertEquals(addRecipe.getTime(), time);
        assertEquals(addRecipe.getDifficulty(), difficulty);
        assertEquals(addRecipe.getComposition(), composition);
        assertEquals(addRecipe.getIngredients(), ingredients);
        assertEquals(addRecipe.getServings(), servings);
        assertEquals(addRecipe.getOrders(), orders);
        assertEquals(addRecipe.getPhoto(), photo);

    }

    @Test
    void 레시피조회() {

        // when
        List<Recipe> recipeList = recipeRepository.findAll();

        // then
        Recipe findRecipe = recipeList.get(0);
        assertEquals(findRecipe.getName(), name);
        assertEquals(findRecipe.getThumbnail(), thumbnail);
        assertEquals(findRecipe.getTime(), time);
        assertEquals(findRecipe.getDifficulty(), difficulty);
        assertEquals(findRecipe.getComposition(), composition);
        assertEquals(findRecipe.getIngredients(), ingredients);
        assertEquals(findRecipe.getServings(), servings);
        assertEquals(findRecipe.getOrders(), orders);
        assertEquals(findRecipe.getPhoto(), photo);
    }

    @Test
    void 레시피재료로조회() {

        // when
        List<Recipe> recipeList = recipeRepository.findByIngredientsContaining("고구마");

        // then
        Recipe findRecipe = recipeList.get(0);
        assertEquals(findRecipe.getName(), name);
        assertEquals(findRecipe.getThumbnail(), thumbnail);
        assertEquals(findRecipe.getTime(), time);
        assertEquals(findRecipe.getDifficulty(), difficulty);
        assertEquals(findRecipe.getComposition(), composition);
        assertEquals(findRecipe.getIngredients(), ingredients);
        assertEquals(findRecipe.getServings(), servings);
        assertEquals(findRecipe.getOrders(), orders);
        assertEquals(findRecipe.getPhoto(), photo);
    }
    
    @Test
    void 레시피이름으로조회() {

        // when
        List<Recipe> recipeList = recipeRepository.findByNameContaining("고구마");

        // then
        Recipe findRecipe = recipeList.get(0);
        assertEquals(findRecipe.getName(), name);
        assertEquals(findRecipe.getThumbnail(), thumbnail);
        assertEquals(findRecipe.getTime(), time);
        assertEquals(findRecipe.getDifficulty(), difficulty);
        assertEquals(findRecipe.getComposition(), composition);
        assertEquals(findRecipe.getIngredients(), ingredients);
        assertEquals(findRecipe.getServings(), servings);
        assertEquals(findRecipe.getOrders(), orders);
        assertEquals(findRecipe.getPhoto(), photo);
    }
}