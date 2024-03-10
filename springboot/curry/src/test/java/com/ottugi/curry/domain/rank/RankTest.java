package com.ottugi.curry.domain.rank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RankTest {
    public static final String KEYWORD = "망고";
    public static final int SCORE = 1;

    public static Rank initRank() {
        return Rank.builder()
                .name(KEYWORD)
                .build();
    }

    private Rank rank;

    @BeforeEach
    public void setUp() {
        rank = initRank();
    }

    @Test
    @DisplayName("랭킹 검색어 추가 테스트")
    void testRank() {
        assertNotNull(rank);
        assertEquals(KEYWORD, rank.getName());
        assertEquals(SCORE, rank.getScore());
    }

    @Test
    @DisplayName("검색어 횟수 증가 테스트")
    void testIncrementScore() {
        rank.incrementScore();

        assertEquals(KEYWORD, rank.getName());
        assertEquals(SCORE + 1, rank.getScore());
    }
}