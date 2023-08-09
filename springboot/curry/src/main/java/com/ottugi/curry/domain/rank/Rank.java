package com.ottugi.curry.domain.rank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Setter
@NoArgsConstructor
@RedisHash(value = "Rank")
public class Rank {

    @Id
    @Indexed
    private String name;
    
    private int score;

    @TimeToLive
    private Long expiredTime;

    @Builder
    public Rank(String name, int score, Long expiredTime) {
        this.name = name;
        this.score = score;
        this.expiredTime = expiredTime;
    }

    public void incrementScore(int score) {
        this.score += score;
    }
}