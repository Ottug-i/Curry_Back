package com.ottugi.curry.service.rank;

import com.ottugi.curry.web.dto.rank.RankResponseDto;

import java.util.List;

public interface RankService {
    void clearRanking();
    void updateOrAddRank(String name);
    List<RankResponseDto> getRankList();
    void weeklyRankingReset();
}
