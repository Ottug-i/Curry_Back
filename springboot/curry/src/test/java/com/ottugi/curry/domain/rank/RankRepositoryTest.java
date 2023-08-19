package com.ottugi.curry.domain.rank;

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

    @Autowired
    RankRepositoryTest(RankRepository rankRepository) {
        this.rankRepository = rankRepository;
    }

    @Test
    void 검색어이름으로조회() {
        // when
        rank = rankRepository.findByName(EXIST_KEYWORD);

        // then
        assertEquals(rank.getName(), EXIST_KEYWORD);
    }

    @Test
    void 검색어횟수내림차순으로조회() {
        // when
        List<Rank> rankList = rankRepository.findAllByOrderByScoreDesc();

        // then
        rank = rankList.get(0);
        assertEquals(rank.getName(), EXIST_KEYWORD);
    }
}