package com.ottugi.curry.domain.rank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor
@RedisHash(value = "rank")
public class Rank {
    @Id
    @Indexed
    private String name;

    private int score;

    @Builder
    public Rank(String name) {
        this.name = name;
        this.score = 1;
    }

    public void incrementScore() {
        this.score += 1;
    }
}