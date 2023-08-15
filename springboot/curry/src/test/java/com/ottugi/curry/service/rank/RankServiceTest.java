package com.ottugi.curry.service.rank;

import com.ottugi.curry.domain.rank.RankRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RankServiceTest {

    @Mock
    private RankRepository rankRepository;

    @InjectMocks
    private RankService rankService;

    @Test
    void 랭킹초기화() {
    }

    @Test
    void 랭킹추가() {
    }

    @Test
    void 랭킹증가() {
    }


    @Test
    void 랭킹목록조회() {
    }

    @Test
    void 일주일마다랭킹초기화() {
    }
}