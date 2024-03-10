package com.ottugi.curry.service.rank;

import com.ottugi.curry.web.dto.rank.RankResponseDto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RankService {
    /**
     * 검색한 레시피 이름에 따른 검색어 랭킹을 추가한다.
     *
     * @param name (검색한 레시피 이름)
     */
    void updateOrAddRank(String name);

    /**
     * 검색어 랭킹 상위 10개를 조회한다.
     *
     * @return List<RankResponseDto> (랭킹 정보를 담은 DTO 리스트)
     */
    @Transactional(readOnly = true)
    List<RankResponseDto> findTopRankList();

    /**
     * 매주 검색어 랭킹을 초기화한다.
     */
    void resetWeeklyRanking();
}
