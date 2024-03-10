package com.ottugi.curry.web.dto.ratings;

import com.ottugi.curry.domain.ratings.Ratings;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class RatingResponseDto {
    @ApiModelProperty(notes = "레시피 아이디", example = "6846342")
    private final long recipeId;

    @ApiModelProperty(notes = "회원 기본키", example = "1")
    private final long userId;

    @ApiModelProperty(notes = "평점", example = "4.0")
    private final double rating;

    public RatingResponseDto(Ratings ratings) {
        this.recipeId = ratings.getRecipeId();
        this.userId = ratings.getUserId();
        this.rating = ratings.getRating();
    }
}
