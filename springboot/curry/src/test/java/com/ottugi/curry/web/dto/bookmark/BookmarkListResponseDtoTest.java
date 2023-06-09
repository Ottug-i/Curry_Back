package com.ottugi.curry.web.dto.bookmark;

import com.ottugi.curry.domain.recipe.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookmarkListResponseDtoTest {

    @Test
    void BookmarkListResponseDto_롬복() {

        // given
        Long recipeId = 1234L;
        String name = "고구마맛탕";
        String thumbnail = "https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/828bccf4fdd0a71b6477a8e96e84906b1.png";
        Time time = Time.ofTime("60분 이내");
        Difficulty difficulty = Difficulty.ofDifficulty("초급");
        Composition composition = Composition.ofComposition("가볍게");
        String ingredients = "[재료] 고구마| 식용유| 황설탕| 올리고당| 견과류| 물";
        Servings servings = Servings.ofServings("2인분");
        String orders = "|1. 바삭하게 튀기는 꿀팁|2. 달콤한 소스 꿀팁|3. 더 건강하게 먹는 꿀팁";
        String photo = "|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/4c9918cf77a109d28b389e6bc753b4bd1.jpg|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/66e8c5f5932e195e7b5405d110a6e67e1.jpg|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/8628264d141fa54487461d41a45d905f1.jpg";

        Boolean isBookmark = true;

        Recipe recipe = Recipe.builder()
                .recipeId(recipeId)
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
        BookmarkListResponseDto bookmarkListResponseDto = new BookmarkListResponseDto(recipe, isBookmark);

        // then
        assertEquals(bookmarkListResponseDto.getRecipeId(), recipeId);
        assertEquals(bookmarkListResponseDto.getName(), name);
        assertEquals(bookmarkListResponseDto.getThumbnail(), thumbnail);
        assertEquals(bookmarkListResponseDto.getTime(), "60분 이내");
        assertEquals(bookmarkListResponseDto.getDifficulty(), "초급");
        assertEquals(bookmarkListResponseDto.getComposition(), "가볍게");
        assertEquals(bookmarkListResponseDto.getIngredients(), ingredients);
        assertEquals(bookmarkListResponseDto.getIsBookmark(), isBookmark);
    }
}