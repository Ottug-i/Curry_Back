package com.ottugi.curry.domain.recipe;

import com.ottugi.curry.except.BaseCode;
import com.ottugi.curry.except.InvalidException;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Composition {
    LIGHTLY("가볍게"),
    CONFIDENTLY("든든하게"),
    GENEROUSLY("푸짐하게");

    private final String compositionName;

    private static final Map<String, Composition> COMPOSITION_MAP = new HashMap<>();

    static {
        for (Composition composition : Composition.values()) {
            COMPOSITION_MAP.put(composition.getCompositionName(), composition);
        }
    }

    public static Composition findByCompositionName(String composition) {
        Composition foundComposition = COMPOSITION_MAP.get(composition);
        if (foundComposition == null) {
            throw new InvalidException(BaseCode.BAD_REQUEST);
        }
        return foundComposition;
    }

    public static boolean matchesComposition(Recipe recipe, String composition) {
        if (composition == null || composition.isBlank()) {
            return true;
        }
        return recipe.getComposition().equals(findByCompositionName(composition));
    }
}