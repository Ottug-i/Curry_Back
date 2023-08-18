package com.ottugi.curry.web.dto.rank;

import com.ottugi.curry.domain.rank.Rank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RankResponseDto {

    @ApiModelProperty(notes = "검색어", example = "달걀")
    private String name;

    @ApiModelProperty(notes = "검색 횟수", example = "1")
    private int score;

    public RankResponseDto(Rank rank) {
        this.name = rank.getName();
        this.score = rank.getScore();
    }
}
