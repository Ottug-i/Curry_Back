package com.ottugi.curry.service.rank;

import com.ottugi.curry.domain.rank.Rank;
import com.ottugi.curry.domain.rank.RankRepository;
import com.ottugi.curry.web.dto.rank.RankResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RankServiceImpl implements RankService {

    private final RankRepository rankRepository;

    // 검색어 랭킹 초기화
    @Override
    public void clear() {
        rankRepository.deleteAll();
    }

    // 검색어 랭킹 추가
    @Override
    public void updateOrAddRank(String name) {

        if (name == null || name.isEmpty()) {
            return;
        }

        Rank rank = rankRepository.findByName(name);
        if (rank != null) {
            rank.incrementScore(1);
            rankRepository.save(rank);
        } else {
            Rank newRank = new Rank();
            newRank.setName(name);
            newRank.setScore(1);
            rankRepository.save(newRank);
        }
    }

    // 검색어 랭킹 목록 조회
    @Override
    public List<RankResponseDto> getRankList() {
        List<RankResponseDto> rankList = new ArrayList<>();
        List<Rank> rankEntities = rankRepository.findTop10ByOrderByScoreDesc();
        for (Rank rank : rankEntities) {
            rankList.add(new RankResponseDto(rank));
        }
        return rankList;
    }

    // 일주일마다 검색어 랭킹 초기화
    @Scheduled(cron = "0 0 0 * * 1")
    @Override
    public void weeklyRankingReset() {
        clear();
    }
}
