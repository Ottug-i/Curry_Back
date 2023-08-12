package com.ottugi.curry.service.rank;

import com.ottugi.curry.domain.rank.Rank;
import com.ottugi.curry.domain.rank.RankRepository;
import com.ottugi.curry.web.dto.rank.RankResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RankServiceImpl implements RankService {

    private final RankRepository rankRepository;

    // 검색어 랭킹 초기화
    @Override
    @Transactional
    public void clearRanking() {
        rankRepository.deleteAll();
    }

    // 검색어 랭킹 추가
    @Override
    @Transactional
    public void updateOrAddRank(String name) {
        if (name == null || name.isEmpty()) {
            return;
        }

        Rank rank = rankRepository.findByName(name);
        if (rank != null) {
            rank.incrementScore(1);
        } else {
            rank = new Rank(name);
        }
        rankRepository.save(rank);
    }

    // 검색어 랭킹 목록 조회
    @Override
    @Transactional(readOnly = true)
    public List<RankResponseDto> getRankList() {
        List<Rank> topRanks = rankRepository.findAllByOrderByScoreDesc();
        int topCount = Math.min(topRanks.size(), 10);
        List<Rank> top10Ranks = topRanks.subList(0, topCount);
        return top10Ranks.stream().map(RankResponseDto::new).collect(Collectors.toList());
    }

    // 일주일마다 검색어 랭킹 초기화
    @Scheduled(cron = "0 0 0 * * 1")
    @Override
    @Transactional
    public void weeklyRankingReset() {
        clearRanking();
    }
}
