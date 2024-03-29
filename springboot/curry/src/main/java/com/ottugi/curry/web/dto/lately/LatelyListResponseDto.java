package com.ottugi.curry.web.dto.lately;

import com.ottugi.curry.domain.lately.Lately;
import com.ottugi.curry.domain.recipe.Recipe;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class LatelyListResponseDto {
    @ApiModelProperty(notes = "레시피 아이디", example = "6842324")
    private final Long recipeId;

    @ApiModelProperty(notes = "레시피 이름", example = "고구마맛탕")
    private final String name;

    @ApiModelProperty(notes = "레시피 썸네일", example = "https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/828bccf4fdd0a71b6477a8e96e84906b1.png")
    private final String thumbnail;

    public LatelyListResponseDto(Lately lately) {
        Recipe recipe = lately.getRecipeId();
        this.recipeId = recipe.getRecipeId();
        this.name = recipe.getName();
        this.thumbnail = recipe.getThumbnail();
    }
}
