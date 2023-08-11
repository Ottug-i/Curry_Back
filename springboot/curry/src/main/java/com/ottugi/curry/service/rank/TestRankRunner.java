package com.ottugi.curry.service.rank;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
public class TestRankRunner implements ApplicationRunner {

    private final RankServiceImpl rankService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // 초기 인기 검색어 데이터 - 추후 삭제
        rankService.clear();

        List<String> nameList = Arrays.asList("달걀빵", "달걀", "연어", "두부", "딸기", "식빵", "돼지고기", "김치", "달걀", "달걀", "연어", "수박", "바나나", "된장");

        for (String name: nameList) {
            rankService.updateOrAddRank(name);
        }
    }
}
