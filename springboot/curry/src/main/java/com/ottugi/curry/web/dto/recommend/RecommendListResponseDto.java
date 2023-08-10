package com.ottugi.curry.web.dto.recommend;

import com.ottugi.curry.domain.recipe.Recipe;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class RecommendListResponseDto {

    @ApiModelProperty(notes = "레시피 아이디", example = "6842324")
    private Long recipeId;

    @ApiModelProperty(notes = "레시피 이름", example = "고구마맛탕")
    private String name;

    @ApiModelProperty(notes = "레시피 썸네일", example = "https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/828bccf4fdd0a71b6477a8e96e84906b1.png")
    private String thumbnail;

    @ApiModelProperty(notes = "레시피 시간", example = "60분 이내")
    private String time;

    @ApiModelProperty(notes = "레시피 난이도", example = "초급")
    private String difficulty;

    @ApiModelProperty(notes = "레시피 구성", example = "가볍게")
    private String composition;

    @ApiModelProperty(notes = "레시피 재료", example = " `[재료] 고구마| 식용유| 황설탕| 올리고당| 견과류| 물")
    private String ingredients;

    public RecommendListResponseDto(Recipe recipe) {
        this.recipeId = recipe.getRecipeId();
        this.name = recipe.getName();
        this.thumbnail = recipe.getThumbnail();
        this.time = recipe.getTime().getTimeName();
        this.difficulty = recipe.getDifficulty().getDifficulty();
        this.composition = recipe.getComposition().getComposition();
        this.ingredients = recipe.getIngredients();
    }
}
