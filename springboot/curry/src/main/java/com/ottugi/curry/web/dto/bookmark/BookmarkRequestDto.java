package com.ottugi.curry.web.dto.bookmark;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkRequestDto {
    @NotNull
    @ApiModelProperty(notes = "회원 기본키", example = "1", required = true)
    private Long userId;

    @NotNull
    @ApiModelProperty(notes = "레시피 아이디", example = "6909678", required = true)
    private Long recipeId;

    @Builder
    public BookmarkRequestDto(Long userId, Long recipeId) {
        this.userId = userId;
        this.recipeId = recipeId;
    }
}
