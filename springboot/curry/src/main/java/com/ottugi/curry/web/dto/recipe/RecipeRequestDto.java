package com.ottugi.curry.web.dto.recipe;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RecipeRequestDto {

    @ApiModelProperty(notes = "회원 기본키", example = "1", required = true)
    private Long userId;

    @ApiModelProperty(notes = "레시피 기본키", example = "[6842324, 6845721]", required = true)
    private List<Long> recipeId;

    @Builder
    public RecipeRequestDto(Long userId, List<Long> recipeId) {
        this.userId = userId;
        this.recipeId = recipeId;
    }
}
