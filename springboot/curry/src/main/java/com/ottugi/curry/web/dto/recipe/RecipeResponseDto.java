package com.ottugi.curry.web.dto.recipe;

import com.ottugi.curry.domain.recipe.Recipe;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class RecipeResponseDto {
    @ApiModelProperty(notes = "레시피 아이디", example = "6842324")
    private final Long recipeId;

    @ApiModelProperty(notes = "레시피 이름", example = "고구마맛탕")
    private final String name;

    @ApiModelProperty(notes = "레시피 썸네일", example = "https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/828bccf4fdd0a71b6477a8e96e84906b1.png")
    private final String thumbnail;

    @ApiModelProperty(notes = "레시피 시간", example = "60분 이내")
    private final String time;

    @ApiModelProperty(notes = "레시피 난이도", example = "초급")
    private final String difficulty;

    @ApiModelProperty(notes = "레시피 구성", example = "가볍게")
    private final String composition;

    @ApiModelProperty(notes = "레시피 재료", example = "`[재료] 고구마| 식용유| 황설탕| 올리고당| 견과류| 물")
    private final String ingredients;

    @ApiModelProperty(notes = "레시피 인분", example = "2인분")
    private final String servings;

    @ApiModelProperty(notes = "레시피 조리 순서", example = "|1. 바삭하게 튀기는 꿀팁|2. 달콤한 소스 꿀팁|3. 더 건강하게 먹는 꿀팁")
    private final String orders;

    @ApiModelProperty(notes = "레시피 사진", example = "|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/4c9918cf77a109d28b389e6bc753b4bd1.jpg|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/66e8c5f5932e195e7b5405d110a6e67e1.jpg|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/8628264d141fa54487461d41a45d905f1.jpg")
    private final String photo;

    @ApiModelProperty(notes = "북마크 유무", example = "true")
    private final Boolean isBookmark;

    public RecipeResponseDto(Recipe recipe, Boolean isBookmark) {
        this.recipeId = recipe.getRecipeId();
        this.name = recipe.getName();
        this.thumbnail = recipe.getThumbnail();
        this.time = recipe.getTime().getTimeName();
        this.difficulty = recipe.getDifficulty().getDifficultyName();
        this.composition = recipe.getComposition().getCompositionName();
        this.ingredients = recipe.getIngredients();
        this.servings = recipe.getServings().getServingName();
        this.orders = recipe.getOrders();
        this.photo = recipe.getPhoto();
        this.isBookmark = isBookmark;
    }
}
