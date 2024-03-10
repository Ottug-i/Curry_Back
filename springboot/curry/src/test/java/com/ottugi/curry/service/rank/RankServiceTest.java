package com.ottugi.curry.service.rank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ottugi.curry.domain.rank.Rank;
import com.ottugi.curry.domain.rank.RankRepository;
import com.ottugi.curry.domain.rank.RankTest;
import com.ottugi.curry.web.dto.rank.RankResponseDto;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RankServiceTest {
    private Rank rank;

    @Mock
    private RankRepository rankRepository;
    @InjectMocks
    private RankServiceImpl rankService;

    @BeforeEach
    public void setUp() {
        rank = RankTest.initRank();
    }

    @Test
    @DisplayName("검색어 순위 추가 테스트")
    void testAddRank() {
        when(rankRepository.findByName(anyString())).thenReturn(null);
        when(rankRepository.save(any(Rank.class))).thenReturn(rank);

        rankService.updateOrAddRank(rank.getName());

        verify(rankRepository, times(1)).findByName(anyString());
        verify(rankRepository, times(1)).save(any(Rank.class));
    }

    @Test
    @DisplayName("검색어 순위 횟수 추가 테스트")
    void testUpdateRank() {
        when(rankRepository.findByName(anyString())).thenReturn(rank);
        when(rankRepository.save(any(Rank.class))).thenReturn(rank);

        rankService.updateOrAddRank(rank.getName());

        verify(rankRepository, times(1)).findByName(anyString());
        verify(rankRepository, times(1)).save(any(Rank.class));
    }

    @Test
    @DisplayName("검색어 최고 순위 10개 목록 조회")
    void testFindTopRankList() {
        when(rankRepository.findAllByOrderByScoreDesc()).thenReturn(Collections.singletonList(rank));

        List<RankResponseDto> result = rankService.findTopRankList();

        assertEquals(1, result.size());
        assertRankResponseDto(result.get(0));

        verify(rankRepository, times(1)).findAllByOrderByScoreDesc();
    }

    @Test
    @DisplayName("일주일마다 검색어 순위 초기화 테스트")
    void testResetWeeklyRanking() {
        doNothing().when(rankRepository).deleteAll();

        rankService.resetWeeklyRanking();

        verify(rankRepository, times(1)).deleteAll();
    }

    private void assertRankResponseDto(RankResponseDto resultDto) {
        assertNotNull(resultDto);
        assertEquals(rank.getName(), resultDto.getName());
        assertEquals(rank.getScore(), resultDto.getScore());
    }
}