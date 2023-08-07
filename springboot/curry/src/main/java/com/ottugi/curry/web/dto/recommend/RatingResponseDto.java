package com.ottugi.curry.web.dto.recommend;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RatingResponseDto {

    @ApiModelProperty(notes = "레시피 아이디", example = "6846342")
    private long recipeId;

    @ApiModelProperty(notes = "회원 기본키", example = "1")
    private long userId;

    @ApiModelProperty(notes = "평점", example = "4.0")
    private double rating;

    public RatingResponseDto(List<Double> ratingInfo) {
        this.recipeId = ratingInfo.get(0).longValue();
        this.userId = ratingInfo.get(1).longValue();
        this.rating = ratingInfo.get(2);
    }
}
