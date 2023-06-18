package com.ottugi.curry.domain.recipe;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Servings {

    oneServing("1인분"),
    twoServing("2인분"),
    threeServing("3인분"),
    fourServing("4인분"),
    fiveServing("5인분"),
    sixServing("6인분이상");

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
