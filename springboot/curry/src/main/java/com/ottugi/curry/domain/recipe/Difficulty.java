package com.ottugi.curry.domain.recipe;

import com.ottugi.curry.except.BaseCode;
import com.ottugi.curry.except.InvalidException;
import java.util.HashMap;
import java.util.Map;
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

    private final String difficultyName;

    private static final Map<String, Difficulty> DIFFICULTY_MAP = new HashMap<>();

    static {
        for (Difficulty difficulty : Difficulty.values()) {
            DIFFICULTY_MAP.put(difficulty.getDifficultyName(), difficulty);
        }
    }

    public static Difficulty findByDifficultyName(String difficulty) {
        Difficulty foundDifficulty = DIFFICULTY_MAP.get(difficulty);
        if (foundDifficulty == null) {
            throw new InvalidException(BaseCode.BAD_REQUEST);
        }
        return foundDifficulty;
    }

    public static boolean matchesDifficulty(Recipe recipe, String difficulty) {
        if (difficulty == null || difficulty.isBlank()) {
            return true;
        }
        return recipe.getDifficulty().equals(findByDifficultyName(difficulty));
    }
}