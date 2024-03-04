package com.ottugi.curry.web.dto.rank;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.TestObjectFactory;
import com.ottugi.curry.domain.rank.Rank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RankResponseDtoTest {
    private Rank rank;

    @BeforeEach
    public void setUp() {
        rank = TestObjectFactory.initRank();
    }

    @Test
    @DisplayName("RankResponseDto 생성 테스트")
    void testRankResponseDto() {
        RankResponseDto rankResponseDto = new RankResponseDto(rank);

        assertEquals(rank.getName(), rankResponseDto.getName());
        assertEquals(rank.getScore(), rankResponseDto.getScore());
    }
}