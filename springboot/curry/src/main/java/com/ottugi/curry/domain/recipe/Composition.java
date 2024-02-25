package com.ottugi.curry.domain.recipe;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Composition {
    LIGHTLY("가볍게"),
    CONFIDENTLY("든든하게"),
    GENEROUSLY("푸짐하게");

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
