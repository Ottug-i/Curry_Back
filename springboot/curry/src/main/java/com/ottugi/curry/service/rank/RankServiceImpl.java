package com.ottugi.curry.service.rank;

import com.ottugi.curry.web.dto.rank.RankResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RankServiceImpl implements RankService {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void clear() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    @Override
    public void addRank(String name) {

        if (name == null || name.isEmpty()) {
            return;
        }

        Double score = 0.0;
        try {
            redisTemplate.opsForZSet().incrementScore("ranking", name, 1);
        } catch (Exception e) {
            System.out.println(e);
        }
        redisTemplate.opsForZSet().incrementScore("ranking", name, score);
    }

    @Override
    public List<RankResponseDto> getRankList() {
        String key = "ranking";
        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> typedTuples = ZSetOperations.reverseRangeWithScores(key, 0, 9);
        return typedTuples.stream().map(RankResponseDto::convertToRankDto).collect(Collectors.toList());
    }

    @Scheduled(cron = "0 0 0 * * 1")
    @Override
    public void weeklyRankingReset() {
        clear();
    }
}
