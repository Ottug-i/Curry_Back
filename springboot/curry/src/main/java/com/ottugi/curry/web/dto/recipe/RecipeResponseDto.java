package com.ottugi.curry.web.dto.recipe;

import com.ottugi.curry.domain.recipe.Recipe;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class RecipeResponseDto {

    @ApiModelProperty(notes = "레시피 기본키", example = "6909678")
    private Long id;

    @ApiModelProperty(notes = "레시피 이름", example = "연어유부초밥")
    private String name;

    @ApiModelProperty(notes = "레시피 썸네일", example = "https://recipe1.ezmember.co.kr/cache/recipe/2019/04/02/f8e2bac1e4e5387b34ef9dfa04f343b41.jpg")
    private String thumbnail;

    @ApiModelProperty(notes = "레시피 시간", example = "15분")
    private String time;

    @ApiModelProperty(notes = "레시피 난이도", example = "초급")
    private String difficulty;

    @ApiModelProperty(notes = "레시피 구성", example = "든든하게")
    private String composition;

    @ApiModelProperty(notes = "레시피 재료", example = "유부초밥###밥###연어")
    private String ingredients;

    @ApiModelProperty(notes = "레시피 인분", example = "1인분")
    private String servings;

    @ApiModelProperty(notes = "레시피 조리 순서", example = "1. 연어는 작게 썬다.###2. 볼에 소스를 만든다.###3. 밥에 배합초를 넣고 버무린다.###4. 유부의 물기를 짜고 밥>소스>연어를 올려 완성한다.")
    private String orders;

    @ApiModelProperty(notes = "레시피 사진", example = "https://recipe1.ezmember.co.kr/cache/recipe/2019/04/01/1617be01e2c10f03acd3dde68c6f954f1.jpg###https://recipe1.ezmember.co.kr/cache/recipe/2019/04/01/0d63f0109b119a2b19d657de551ac10f1.jpg###https://recipe1.ezmember.co.kr/cache/recipe/2019/04/01/d576fafdfe011986ea97ea2c2f6040311.jpg###https://recipe1.ezmember.co.kr/cache/recipe/2019/04/01/d576fafdfe011986ea97ea2c2f6040311.jpg")
    private String photo;

    @ApiModelProperty(notes = "북마크 유무", example = "true")
    private Boolean isBookmark;

    public RecipeResponseDto(Recipe recipe, Boolean isBookmark) {
        this.id = recipe.getId();
        this.name = recipe.getName();
        this.thumbnail = recipe.getThumbnail();
        this.time = recipe.getTime();
        this.difficulty = recipe.getDifficulty();
        this.composition = recipe.getComposition();
        this.ingredients = recipe.getIngredients();
        this.servings = recipe.getServings();
        this.orders = recipe.getOrders();
        this.photo = recipe.getPhoto();
        this.isBookmark = isBookmark;
    }
}
