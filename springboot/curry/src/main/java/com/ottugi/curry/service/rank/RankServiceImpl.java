package com.ottugi.curry.service.rank;

import com.ottugi.curry.domain.rank.Rank;
import com.ottugi.curry.domain.rank.RankRepository;
import com.ottugi.curry.web.dto.rank.RankResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RankServiceImpl implements RankService {
    private static final int RANK_SIZE = 10;

    private final RankRepository rankRepository;

    @Override
    public void updateOrAddRank(String name) {
        if (isEmptySearchKeyword(name)) {
            return;
        }
        Rank rank = rankRepository.findByName(name);
        if (rank == null) {
            createRank(name);
        } else {
            incrementRankScore(rank);
        }
    }

    @Override
    public List<RankResponseDto> findTopRankList() {
        List<Rank> topRanks = rankRepository.findAllByOrderByScoreDesc();
        int rankSize = Math.min(topRanks.size(), RANK_SIZE);
        List<Rank> top10Ranks = topRanks.subList(0, rankSize);
        return top10Ranks.stream().map(RankResponseDto::new).collect(Collectors.toList());
    }

    @Override
    @Scheduled(cron = "0 0 0 * * 1")
    public void resetWeeklyRanking() {
        rankRepository.deleteAll();
    }

    private Boolean isEmptySearchKeyword(String name) {
        return name == null || name.isEmpty();
    }

    private void createRank(String name) {
        Rank newRank = Rank.builder().name(name).build();
        rankRepository.save(newRank);
    }

    private void incrementRankScore(Rank exisitingRank) {
        exisitingRank.incrementScore();
        rankRepository.save(exisitingRank);
    }
}
