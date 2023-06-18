package com.ottugi.curry.web.dto.bookmark;

import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.Time;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class BookmarkListResponseDto {

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

    @ApiModelProperty(notes = "북마크 유무", example = "true")
    private Boolean isBookmark;

    public BookmarkListResponseDto(Recipe recipe, Boolean isBookmark) {
        this.id = recipe.getId();
        this.name = recipe.getName();
        this.thumbnail = recipe.getThumbnail();
        this.time = recipe.getTime().getTimeName();
        this.difficulty = recipe.getDifficulty().getDifficulty();
        this.composition = recipe.getComposition().getComposition();
        this.ingredients = recipe.getIngredients();
        this.isBookmark = isBookmark;
    }
}
