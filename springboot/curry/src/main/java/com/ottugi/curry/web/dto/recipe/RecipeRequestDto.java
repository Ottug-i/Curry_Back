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

    @ApiModelProperty(notes = "재료", example = "[달걀, 베이컨, 양파]", required = true)
    private List<String> ingredients;

    @ApiModelProperty(notes = "페이지 번호", example = "1", required = true)
    private int page;

    @ApiModelProperty(notes = "페이지 사이즈", example = "10", required = true)
    private int size;

    @Builder
    public RecipeRequestDto(Long userId, List<String> ingredients, int page, int size) {
        this.userId = userId;
        this.ingredients = ingredients;
        this.page = page;
        this.size = size;
    }
}
