package com.ottugi.curry.domain.recipe;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Difficulty {
    ANYONE("아무나"),
    BEGINNER("초급"),
    MIDDLE("중급"),
    ADVANCED("고급"),
    MASTER("마스터");

    private String difficulty;

    Difficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public static Difficulty ofDifficulty(String difficulty) {
        return Arrays.stream(Difficulty.values())
                .filter(d -> d.getDifficulty().equals(difficulty))
                .findAny().orElse(null);
    }

    public static Boolean isDifficultyMatching(Recipe recipe, String difficulty) {
        return recipe.getDifficulty().getDifficulty().contains(difficulty);
    }
}
