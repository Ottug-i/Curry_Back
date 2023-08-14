package com.ottugi.curry.domain.rank;

import com.ottugi.curry.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor
class RankRepositoryTest {

    private Rank rank;

    private final TestConstants testConstants;
    private final RankRepository rankRepository;

    @Test
    void 검색어이름으로조회() {
        // when
        rank = rankRepository.findByName("달걀");

        // then
        assertEquals(rank.getName(), "달걀");
    }

    @Test
    void 검색어횟수내림차순으로조회() {
        // when
        List<Rank> rankList = rankRepository.findAllByOrderByScoreDesc();

        // then
        rank = rankList.get(0);
        assertEquals(rank.getName(), "달걀");
    }
}