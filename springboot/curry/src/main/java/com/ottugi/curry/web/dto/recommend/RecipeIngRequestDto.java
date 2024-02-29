package com.ottugi.curry.web.dto.recommend;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeIngRequestDto {
    @NotNull
    @ApiModelProperty(notes = "회원 기본키", example = "1", required = true)
    private Long userId;

    @NotBlank
    @ApiModelProperty(notes = "재료", example = "[달걀, 베이컨, 양파]", required = true)
    private List<String> ingredients;

    @NotBlank
    @ApiModelProperty(notes = "레시피 시간", example = "10분 이내")
    private String time;

    @NotBlank
    @ApiModelProperty(notes = "레시피 난이도", example = "아무나")
    private String difficulty;

    @NotBlank
    @ApiModelProperty(notes = "레시피 구성", example = "가볍게")
    private String composition;

    @NotNull
    @ApiModelProperty(notes = "페이지 번호", example = "1", required = true)
    private int page;

    @NotNull
    @ApiModelProperty(notes = "페이지 사이즈", example = "10", required = true)
    private int size;

    @Builder
    public RecipeIngRequestDto(Long userId, List<String> ingredients, String time, String difficulty, String composition,
                               int page, int size) {
        this.userId = userId;
        this.ingredients = ingredients;
        this.time = time;
        this.difficulty = difficulty;
        this.composition = composition;
        this.page = page;
        this.size = size;
    }
}
