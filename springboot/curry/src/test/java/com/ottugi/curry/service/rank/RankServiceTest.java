package com.ottugi.curry.service.rank;

import com.ottugi.curry.domain.rank.Rank;
import com.ottugi.curry.domain.rank.RankRepository;
import com.ottugi.curry.web.dto.rank.RankResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
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
    private RankService rankService;

    @BeforeEach
    public void setUp() {
        // given
        rank = new Rank();
    }

    @Test
    void 랭킹초기화() {
        // when
        doNothing().when(rankRepository).deleteAll();

        rankService.clearRanking();

        // then
        verify(rankRepository, times(1)).deleteAll();
    }

    @Test
    void 랭킹추가() {
        // when
        when(rankRepository.findByName(EXIST_KEYWORD)).thenReturn(null);
        when(rankRepository.save(rank)).thenReturn(rank);

        rankService.updateOrAddRank(EXIST_KEYWORD);

        // then
        verify(rankRepository, times(1)).save(rank);
    }

    @Test
    void 랭킹증가() {
        // when
        when(rankRepository.findByName(EXIST_KEYWORD)).thenReturn(rank);

        rankService.updateOrAddRank(EXIST_KEYWORD);

        // then
        verify(rankRepository, times(1)).save(rank);
    }

    @Test
    void 랭킹목록조회() {
        // when
        when(rankRepository.findAllByOrderByScoreDesc()).thenReturn(rankList);

        List<RankResponseDto> rankResponseDtos = rankService.getRankList();

        // then
        assertNotNull(rankResponseDtos);
        assertEquals(rankResponseDtos.size(), 10);
    }

    @Test
    void 일주일마다랭킹초기화() {
        // when
        doNothing().when(rankRepository).deleteAll();
        when(taskScheduler.schedule(any(Runnable.class), any(Date.class))).thenReturn(null);

        Instant instant = Instant.parse("2023-08-21T00:00:00Z");
        ReflectionTestUtils.setField(rankService, EXIST_KEYWORD, instant);

        rankService.weeklyRankingReset();

        // then
        verify(rankRepository, times(1)).deleteAll();
        verify(taskScheduler, times(1)).schedule(any(Runnable.class), any(Date.class));
    }
}