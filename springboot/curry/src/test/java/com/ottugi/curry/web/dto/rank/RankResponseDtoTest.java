package com.ottugi.curry.web.dto.rank;

import com.ottugi.curry.domain.rank.Rank;
import org.junit.jupiter.api.Test;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class RankResponseDtoTest {

    private Rank rank;

    @Test
    void RankResponseDto_롬복() {
        // given
        rank = Rank.builder()
                .name(KEYWORD)
                .build();

        // when
        RankResponseDto rankResponseDto = new RankResponseDto(rank);

        // then
        assertEquals(rankResponseDto.getName(), KEYWORD);
        assertEquals(rankResponseDto.getScore(), SCORE);
    }
}