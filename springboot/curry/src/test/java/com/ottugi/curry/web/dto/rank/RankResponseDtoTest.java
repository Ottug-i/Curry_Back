package com.ottugi.curry.web.dto.rank;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.domain.rank.Rank;
import com.ottugi.curry.domain.rank.RankTest;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RankResponseDtoTest {
    private static RankResponseDto initRankResponseDto(Rank rank) {
        return new RankResponseDto(rank);
    }

    public static List<RankResponseDto> initRankResponseDtoList(Rank rank) {
        return Collections.singletonList(initRankResponseDto(rank));
    }

    private Rank rank;

    @BeforeEach
    public void setUp() {
        rank = RankTest.initRank();
    }

    @Test
    @DisplayName("RankResponseDto 생성 테스트")
    void testRankResponseDto() {
        RankResponseDto rankResponseDto = initRankResponseDto(rank);

        assertEquals(rank.getName(), rankResponseDto.getName());
        assertEquals(rank.getScore(), rankResponseDto.getScore());
    }
}