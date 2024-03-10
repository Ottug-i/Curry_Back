package com.ottugi.curry.domain.recipe;

import com.ottugi.curry.except.BaseCode;
import com.ottugi.curry.except.InvalidException;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Servings {
    ONE("1인분"),
    TWO("2인분"),
    THREE("3인분"),
    FOUR("4인분"),
    FIVE("5인분"),
    SIX("6인분이상");

    private final String servingName;

    private static final Map<String, Servings> SERVINGS_MAP = new HashMap<>();

    static {
        for (Servings servings : Servings.values()) {
            SERVINGS_MAP.put(servings.getServingName(), servings);
        }
    }

    public static Servings findByServingName(String servings) {
        Servings foundServings = SERVINGS_MAP.get(servings);
        if (foundServings == null) {
            throw new InvalidException(BaseCode.BAD_REQUEST);
        }
        return foundServings;
    }
}
