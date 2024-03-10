package com.ottugi.curry.web.dto.rank;

import com.ottugi.curry.domain.rank.Rank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class RankResponseDto {
    @ApiModelProperty(notes = "레시피 검색어 이름", example = "달걀")
    private final String name;

    @ApiModelProperty(notes = "검색 횟수", example = "1")
    private final int score;

    public RankResponseDto(Rank rank) {
        this.name = rank.getName();
        this.score = rank.getScore();
    }
}
