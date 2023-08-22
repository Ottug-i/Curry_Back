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
        rank = new Rank(KEYWORD);
    }

    @Test
    void 검색어_추가() {
        // when, then
        assertEquals(KEYWORD, rank.getName());
        assertEquals(SCORE, rank.getScore());
    }

    @Test
    void 검색어_횟수_증가() {
        // when
        rank.incrementScore(SCORE);

        // then
        assertEquals(KEYWORD, rank.getName());
        assertEquals(SCORE + 1, rank.getScore());
    }
}