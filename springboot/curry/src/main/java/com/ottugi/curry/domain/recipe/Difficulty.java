package com.ottugi.curry.domain.recipe;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Difficulty {

    anyone("아무나"),
    beginner("초급"),
    middle("중급"),
    advanced("고급"),
    master("마스터");

    private String difficulty;

    Difficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public static Difficulty ofDifficulty(String difficulty) {
        return Arrays.stream(Difficulty.values())
                .filter(d -> d.getDifficulty().equals(difficulty))
                .findAny().orElse(null);
    }

}
