package com.ottugi.curry.domain.rank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RankTest {

    private Rank rank;

    @BeforeEach
    public void setUp() {
        // given
        rank = new Rank(NEW_KEYWORD);
    }

    @Test
    void 검색어랭킹추가() {
        // when, then
        assertEquals(rank.getName(), NEW_KEYWORD);
        assertEquals(rank.getScore(), SCORE);
    }

    @Test
    void 검색어랭킹증가() {
        // when
        rank.incrementScore(SCORE);

        // then
        assertEquals(rank.getName(), NEW_KEYWORD);
        assertEquals(rank.getScore(), rank.getScore() + 1);
    }
}