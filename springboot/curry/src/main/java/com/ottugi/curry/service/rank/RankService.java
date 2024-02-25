package com.ottugi.curry.service.rank;

import com.ottugi.curry.web.dto.rank.RankResponseDto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RankService {
    void updateOrAddRank(String name);

    @Transactional(readOnly = true)
    List<RankResponseDto> findTopRankList();

    void resetWeeklyRanking();
}
