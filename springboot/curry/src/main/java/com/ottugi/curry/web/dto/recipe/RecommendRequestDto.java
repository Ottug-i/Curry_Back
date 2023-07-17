package com.ottugi.curry.web.dto.recipe;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RecommendRequestDto {

    @ApiModelProperty(notes = "회원 기본키", example = "1", required = true)
    private Long userId;

    @ApiModelProperty(notes = "레시피 아이디 리스트", example = "[6842324, 6845721, 6845906, 6846020, 6846262, 6846342, 6846428]", required = true)
    private List<Long> recipeId;

    @Builder
    public RecommendRequestDto(Long userId, List<Long> recipeId) {
        this.userId = userId;
        this.recipeId = recipeId;
    }
}