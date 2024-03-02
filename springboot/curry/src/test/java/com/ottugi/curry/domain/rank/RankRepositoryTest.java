package com.ottugi.curry.domain.rank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ottugi.curry.TestObjectFactory;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class RankRepositoryTest {
    private Rank rank;

    @Autowired
    private RankRepository rankRepository;

    @BeforeEach
    public void setUp() {
        rank = TestObjectFactory.initRank();
        rank = rankRepository.save(rank);
    }

    @AfterEach
    public void clean() {
        rankRepository.deleteAll();
    }

    @Test
    void 이름으로_검색어_조회() {
        Rank foundRank = rankRepository.findByName(rank.getName());

        assertEquals(rank.getName(), foundRank.getName());
    }

    @Test
    void 검색어_순위_내림차순_조회() {
        List<Rank> foundRankList = rankRepository.findAllByOrderByScoreDesc();

        assertNotNull(foundRankList);
    }
}