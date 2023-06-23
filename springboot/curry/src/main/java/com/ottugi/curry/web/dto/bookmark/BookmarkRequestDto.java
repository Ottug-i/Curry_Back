package com.ottugi.curry.web.dto.bookmark;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookmarkRequestDto {

    @ApiModelProperty(notes = "회원 기본키", example = "1", required = true)
    Long userId;

    @ApiModelProperty(notes = "레시피 아이디", example = "6909678", required = true)
    Long recipeId;

    @Builder
    public BookmarkRequestDto(Long userId, Long recipeId) {
        this.userId = userId;
        this.recipeId = recipeId;
    }
}
