package com.ottugi.curry.web.dto.ratings;

import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RatingRequestDto {
    @NotNull
    @ApiModelProperty(notes = "회원 기본키", example = "1", required = true)
    private Long userId;

    @NotEmpty
    @ApiModelProperty(notes = "레시피에 따른 평점 정보", required = true)
    private Map<Long, Double> newUserRatingsDic;

    @Builder
    public RatingRequestDto(Long userId, Map<Long, Double> newUserRatingsDic) {
        this.userId = userId;
        this.newUserRatingsDic = newUserRatingsDic;
    }
}
