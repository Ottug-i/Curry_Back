package com.ottugi.curry.domain.rank;

import static com.ottugi.curry.TestConstants.KEYWORD;
import static com.ottugi.curry.TestConstants.SCORE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ottugi.curry.TestObjectFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RankTest {
    private Rank rank;

    @BeforeEach
    public void setUp() {
        rank = TestObjectFactory.initRank();
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