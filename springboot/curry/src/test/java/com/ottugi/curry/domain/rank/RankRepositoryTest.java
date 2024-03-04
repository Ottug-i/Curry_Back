package com.ottugi.curry.domain.rank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ottugi.curry.RedisMockConfig;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(RedisMockConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RankRepositoryTest {
    private Rank rank;

    @Autowired
    private RankRepository rankRepository;

    @BeforeEach
    public void setUp() throws IOException {
        rank = RankTest.initRank();
        rank = rankRepository.save(rank);
    }

    @AfterEach
    public void clean() {
        rankRepository.deleteAll();
    }

    @Test
    @DisplayName("이름으로 검색어 조회")
    void testFindByName() {
        Rank foundRank = rankRepository.findByName(rank.getName());

        assertEquals(rank.getName(), foundRank.getName());
        assertEquals(rank.getScore(), foundRank.getScore());
    }

    @Test
    @DisplayName("검색어 순위 목록 내림차순 조회")
    void testFindAllByOrderByScoreDesc() {
        List<Rank> foundRankList = rankRepository.findAllByOrderByScoreDesc();

        assertNotNull(foundRankList);
    }
}