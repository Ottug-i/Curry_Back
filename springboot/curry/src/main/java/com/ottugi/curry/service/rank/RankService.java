package com.ottugi.curry.service.rank;

import com.ottugi.curry.web.dto.rank.RankResponseDto;
import java.util.List;

public interface RankService {

    void clear();
    void addRank(String name);
    List<RankResponseDto> getRankList();
    void weeklyRankingReset();
}
