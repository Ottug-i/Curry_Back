package com.ottugi.curry.service.rank;

import com.ottugi.curry.domain.rank.Rank;
import com.ottugi.curry.domain.rank.RankRepository;
import com.ottugi.curry.web.dto.rank.RankResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.TaskScheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RankServiceTest {

    private Rank rank;

    private List<Rank> rankList = new ArrayList<>();

    @Mock
    private RankRepository rankRepository;

    @Mock
    private TaskScheduler taskScheduler;

    @InjectMocks
    private RankServiceImpl rankService;

    @BeforeEach
    public void setUp() {
        // given
        rank = new Rank(KEYWORD);

        rankList.add(rank);
    }

    @AfterEach
    public void clean() {
        // clean
        rankRepository.deleteAll();
    }

    @Test
    void 랭킹초기화() {
        // given
        when(rankRepository.save(any(Rank.class))).thenReturn(rank);
        doNothing().when(rankRepository).deleteAll();

        // when
        rankService.clearRanking();
    }

    @Test
    void 랭킹추가() {
        // given
        when(rankRepository.findByName(anyString())).thenReturn(null);
        when(rankRepository.save(any(Rank.class))).thenReturn(rank);

        // when
        rankService.updateOrAddRank(rank.getName());
    }

    @Test
    void 랭킹증가() {
        // given
        when(rankRepository.save(any(Rank.class))).thenReturn(rank);
        when(rankRepository.findByName(rank.getName())).thenReturn(rank);

        // when
        rankService.updateOrAddRank(rank.getName());
    }

    @Test
    void 랭킹목록조회() {
        // given
        when(rankRepository.save(any(Rank.class))).thenReturn(rank);
        when(rankRepository.findAllByOrderByScoreDesc()).thenReturn(rankList);

        // when
        List<RankResponseDto> testRankResponseDtoList = rankService.getRankList();

        // then
        assertNotNull(testRankResponseDtoList);
        assertEquals(rankList.size(), testRankResponseDtoList.size());
    }

    @Test
    void 일주일마다랭킹초기화() {
        // given
        when(rankRepository.save(any(Rank.class))).thenReturn(rank);
        doNothing().when(rankRepository).deleteAll();
        when(taskScheduler.schedule(any(Runnable.class), any(Date.class))).thenReturn(null);

        // when
        rankService.weeklyRankingReset();
    }
}