package com.ottugi.curry.web.dto.rank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.ZSetOperations;

@Getter
@Setter
@NoArgsConstructor
public class RankResponseDto {

    @ApiModelProperty(notes = "검색어", example = "달걀")
    private String name;

    @ApiModelProperty(notes = "검색 횟수", example = "1")
    private int score;

    public static RankResponseDto convertToRankDto(ZSetOperations.TypedTuple typedTuple) {
        RankResponseDto rankResponseDto = new RankResponseDto();
        rankResponseDto.name = typedTuple.getValue().toString();
        rankResponseDto.score = typedTuple.getScore().intValue();
        return rankResponseDto;
    }
}
