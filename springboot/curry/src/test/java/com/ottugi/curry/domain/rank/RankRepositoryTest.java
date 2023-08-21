package com.ottugi.curry.domain.rank;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RankRepositoryTest {

    private Rank rank;

    private RankRepository rankRepository;

    private Rank testRank;

    @Autowired
    RankRepositoryTest(RankRepository rankRepository) {
        this.rankRepository = rankRepository;
    }

    @BeforeEach
    public void setUp() {
        // given
        rank = new Rank(KEYWORD);
        rank = rankRepository.save(rank);
    }

    @AfterEach
    public void clean() {
        // clean
        rankRepository.deleteAll();
    }

    @Test
    void 검색어이름으로조회() {
        // when
        testRank = rankRepository.findByName(rank.getName());

        // then
        assertEquals(rank.getName(), testRank.getName());
    }

    @Test
    void 검색어횟수내림차순으로조회() {
        // when
        List<Rank> rankList = rankRepository.findAllByOrderByScoreDesc();

        // then
        assertNotNull(rankList);
    }
}