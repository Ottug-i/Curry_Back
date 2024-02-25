package com.ottugi.curry.domain.recipe;

import java.util.Arrays;
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

    private String servings;

    Servings(String servings) {
        this.servings = servings;
    }

    public static Servings ofServings(String servings) {
        return Arrays.stream(Servings.values())
                .filter(s -> s.getServings().equals(servings))
                .findAny().orElse(null);
    }
}
