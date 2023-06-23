package com.ottugi.curry.domain.recipe;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecipeTest {

    private final Long recipeId = 6842324L;
    private final String name = "고구마맛탕";
    private final String thumbnail = "https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/828bccf4fdd0a71b6477a8e96e84906b1.png";
    private final String time = "60분 이내";
    private final String difficulty = "초급";
    private final String composition = "가볍게";
    private final String ingredients = "[재료] 고구마| 식용유| 황설탕| 올리고당| 견과류| 물";
    private final String servings = "2인분";
    private final String orders = "|1. 바삭하게 튀기는 꿀팁|2. 달콤한 소스 꿀팁|3. 더 건강하게 먹는 꿀팁";
    private final String photo = "|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/4c9918cf77a109d28b389e6bc753b4bd1.jpg|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/66e8c5f5932e195e7b5405d110a6e67e1.jpg|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/8628264d141fa54487461d41a45d905f1.jpg";

    @Test
    void 레시피추가() {

        // given
        Recipe recipe = Recipe.builder()
                .id(recipeId)
                .name(name)
                .thumbnail(thumbnail)
                .time(Time.ofTime(time))
                .difficulty(Difficulty.ofDifficulty(difficulty))
                .composition(Composition.ofComposition(composition))
                .ingredients(ingredients)
                .servings(Servings.ofServings(servings))
                .orders(orders)
                .photo(photo)
                .build();

        // when, then
        assertEquals(recipe.getId(), recipeId);
        assertEquals(recipe.getName(), name);
        assertEquals(recipe.getThumbnail(), thumbnail);
        assertEquals(recipe.getTime(), time);
        assertEquals(recipe.getDifficulty(), difficulty);
        assertEquals(recipe.getComposition(), composition);
        assertEquals(recipe.getIngredients(), ingredients);
        assertEquals(recipe.getServings(), servings);
        assertEquals(recipe.getOrders(), orders);
        assertEquals(recipe.getPhoto(), photo);
    }

}