package com.ottugi.curry.web.dto.recipe;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecipeSaveRequestDto {

    @ApiModelProperty(notes = "레시피 아이디", example = "6842324")
    private Long recipeId;

    @ApiModelProperty(notes = "레시피 이름", example = "고구마맛탕")
    private String name;

    @ApiModelProperty(notes = "레시피 구성", example = "가볍게")
    private String composition;

    @ApiModelProperty(notes = "레시피 재료", example = "[재료] 고구마| 식용유| 황설탕| 올리고당| 견과류| 물")
    private String ingredients;

    @ApiModelProperty(notes = "레시피 인분", example = "2인분")
    private String servings;

    @ApiModelProperty(notes = "레시피 난이도", example = "초급")
    private String difficulty;

    @ApiModelProperty(notes = "레시피 썸네일", example = "https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/828bccf4fdd0a71b6477a8e96e84906b1.png")
    private String thumbnail;

    @ApiModelProperty(notes = "레시피 시간", example = "60분 이내")
    private String time;

    @ApiModelProperty(notes = "레시피 조리 순서", example = "|1. 바삭하게 튀기는 꿀팁|2. 달콤한 소스 꿀팁|3. 더 건강하게 먹는 꿀팁")
    private String orders;

    @ApiModelProperty(notes = "레시피 사진", example = "|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/4c9918cf77a109d28b389e6bc753b4bd1.jpg|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/66e8c5f5932e195e7b5405d110a6e67e1.jpg|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/8628264d141fa54487461d41a45d905f1.jpg")
    private String photo;

    @Builder
    public RecipeSaveRequestDto(Long recipeId, String name, String composition, String ingredients, String servings, String difficulty, String thumbnail, String time, String orders, String photo) {
        this.recipeId = recipeId;
        this.name = name;
        this.composition = composition;
        this.ingredients = ingredients;
        this.servings = servings;
        this.difficulty = difficulty;
        this.thumbnail = thumbnail;
        this.time = time;
        this.orders = orders;
        this.photo = photo;
    }
}
