package com.ottugi.curry.web.dto.rank;

import com.ottugi.curry.domain.rank.Rank;
import org.junit.jupiter.api.Test;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class RankResponseDtoTest {

    private Rank rank;

    @Test
    void 검색어_응답_Dto_롬복() {
        // given
        rank = Rank.builder()
                .name(KEYWORD)
                .build();

        // when
        RankResponseDto rankResponseDto = new RankResponseDto(rank);

        // then
        assertEquals(rank.getName(), rankResponseDto.getName());
        assertEquals(rank.getScore(), rankResponseDto.getScore());
    }
}