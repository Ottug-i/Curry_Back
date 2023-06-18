package com.ottugi.curry.domain.recipe;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Composition {

    lightly("가볍게"),
    confidently("든든하게"),
    generously("푸짐하게");

    private String composition;

    Composition(String composition) {
        this.composition = composition;
    }

    public static Composition ofComposition(String composition) {
        return Arrays.stream(Composition.values())
                .filter(c -> c.getComposition().equals(composition))
                .findAny().orElse(null);
    }
}
