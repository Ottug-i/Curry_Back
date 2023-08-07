package com.ottugi.curry.web.dto.recommend;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class RatingRequestDto {

    @ApiModelProperty(notes = "회원 기본키", example = "1", required = true)
    @JsonProperty(value = "user_id")
    private Long userId;

    @ApiModelProperty(notes = "레시피에 따른 평점 정보", required = true)
    @JsonProperty(value = "new_user_ratings_dic")
    private Map<Long, Double> newUserRatingsDic;

    @Builder
    public RatingRequestDto(Long userId, Map<Long, Double> newUserRatingsDic) {
        this.userId = userId;
        this.newUserRatingsDic = newUserRatingsDic;
    }
}
