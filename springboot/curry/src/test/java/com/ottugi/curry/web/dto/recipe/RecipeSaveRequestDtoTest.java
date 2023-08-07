package com.ottugi.curry.web.dto.recipe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeSaveRequestDtoTest {

    @Test
    void RecipeSaveRequestDto_롬복() {

        // given
        Long recipeId = 1234L;
        String name = "고구마맛탕";
        String thumbnail = "https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/828bccf4fdd0a71b6477a8e96e84906b1.png";
        String time = "60분 이내";
        String difficulty = "초급";
        String composition = "가볍게";
        String ingredients = "[재료] 고구마| 식용유| 황설탕| 올리고당| 견과류| 물";
        String servings ="2인분";
        String orders = "|1. 바삭하게 튀기는 꿀팁|2. 달콤한 소스 꿀팁|3. 더 건강하게 먹는 꿀팁";
        String photo = "|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/4c9918cf77a109d28b389e6bc753b4bd1.jpg|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/66e8c5f5932e195e7b5405d110a6e67e1.jpg|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/8628264d141fa54487461d41a45d905f1.jpg";

        // when
        RecipeSaveRequestDto recipeSaveRequestDto = new RecipeSaveRequestDto(recipeId, name, composition, ingredients, servings, difficulty, thumbnail, time, orders, photo);

        // then
        assertEquals(recipeSaveRequestDto.getRecipeId(), recipeId);
        assertEquals(recipeSaveRequestDto.getName(), name);
        assertEquals(recipeSaveRequestDto.getComposition(), composition);
        assertEquals(recipeSaveRequestDto.getIngredients(), ingredients);
        assertEquals(recipeSaveRequestDto.getServings(), servings);
        assertEquals(recipeSaveRequestDto.getDifficulty(), difficulty);
        assertEquals(recipeSaveRequestDto.getThumbnail(), thumbnail);
        assertEquals(recipeSaveRequestDto.getTime(), time);
        assertEquals(recipeSaveRequestDto.getOrders(), orders);
        assertEquals(recipeSaveRequestDto.getPhoto(), photo);
    }
}