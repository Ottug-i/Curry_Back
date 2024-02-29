package com.ottugi.curry.web.dto.recipe;

import com.ottugi.curry.domain.recipe.Genre;
import com.ottugi.curry.domain.recipe.Recipe;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class RecipeListResponseDto {
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

    @ApiModelProperty(notes = "레시피 재료", example = " `[재료] 고구마| 식용유| 황설탕| 올리고당| 견과류| 물")
    private final String ingredients;

    @ApiModelProperty(notes = "북마크 유무", example = "true")
    private final Boolean isBookmark;

    @ApiModelProperty(notes = "재료 메인 장르", example = "vegetable")
    private final String mainGenre;

    public RecipeListResponseDto(Recipe recipe, Boolean isBookmark) {
        this.recipeId = recipe.getRecipeId();
        this.name = recipe.getName();
        this.thumbnail = recipe.getThumbnail();
        this.time = recipe.getTime().getTimeName();
        this.difficulty = recipe.getDifficulty().getDifficulty();
        this.composition = recipe.getComposition().getComposition();
        this.ingredients = recipe.getIngredients();
        this.isBookmark = isBookmark;
        this.mainGenre = Genre.findMainGenre(recipe);
    }
}
