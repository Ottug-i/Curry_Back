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

    private final Long id = 1234L;
    private final String name = "참치마요 덮밥";
    private final String thumbnail = "www";
    private final String time = "15분";
    private final String difficulty = "초급";
    private final String composition = "든든하게";
    private final String ingredients = "참치캔###마요네즈###쪽파";
    private final String seasoning = "진간장###올리고당###설탕###";
    private final String orders = "1. 기름 뺀 참치###2. 마요네즈 4.5큰 술###3. 잘 비벼주세요.";
    private final String photo = "www###wwww####wwww";
    private Recipe recipe;

    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    public void setUp() {

        // given
        Recipe recipe = Recipe.builder()
                .id(id)
                .name(name)
                .thumbnail(thumbnail)
                .time(Time.ofTime(time))
                .difficulty(Difficulty.ofDifficulty(difficulty))
                .composition(Composition.ofComposition(composition))
                .ingredients(ingredients)
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
                .id(id)
                .name(name)
                .thumbnail(thumbnail)
                .time(Time.ofTime(time))
                .difficulty(Difficulty.ofDifficulty(difficulty))
                .composition(Composition.ofComposition(composition))
                .ingredients(ingredients)
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
        assertEquals(findRecipe.getOrders(), orders);
        assertEquals(findRecipe.getPhoto(), photo);
    }

    @Test
    void 레시피아이디로조회() {

        // when
        List<Recipe> recipeList = recipeRepository.findByIdIn(Arrays.asList("달걀"));

        // then
        Recipe findRecipe = recipeList.get(0);
        assertEquals(findRecipe.getName(), name);
        assertEquals(findRecipe.getThumbnail(), thumbnail);
        assertEquals(findRecipe.getTime(), time);
        assertEquals(findRecipe.getDifficulty(), difficulty);
        assertEquals(findRecipe.getComposition(), composition);
        assertEquals(findRecipe.getIngredients(), ingredients);
        assertEquals(findRecipe.getOrders(), orders);
        assertEquals(findRecipe.getPhoto(), photo);
    }
}