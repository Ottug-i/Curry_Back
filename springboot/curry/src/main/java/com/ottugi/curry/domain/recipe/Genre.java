package com.ottugi.curry.domain.recipe;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Genre {
    MEAT(List.of("ing1", "ing2", "ing3", "ing4"), "meat"),
    FISH(List.of("ing5", "ing6", "ing7", "ing8"), "fish"),
    KIMCHI(List.of("ing9"), "kimchi"),
    TOFU(List.of("ing10"), "tofu"),
    EGG(List.of("ing11"), "egg"),
    MUSHROOM(List.of("ing12"), "mushroom"),
    VEGETABLE(List.of("ing12", "ing13", "ing14", "ing15", "ing16", "ing17", "ing18"), "vegetable"),
    FRUIT(List.of("ing19", "ing20", "ing21", "ing22", "ing23", "ing24"), "vegetable"),
    MILK(List.of("ing25"), "egg");

    private final List<String> ingredientNumber;
    private final String genre;

    public static Genre ofGenre(String number) {
        return Arrays.stream(Genre.values())
                .filter(g -> g.getIngredientNumber().contains(number))
                .findAny().orElse(null);
    }

    public static String findMainGenre(Recipe recipe) {
        final String SPLIT_VALUE = "\\|";
        String[] parts = recipe.getGenre().split(SPLIT_VALUE);
        if (parts.length > 0) {
            String mainGenre = parts[0];
            return ofGenre(mainGenre).getGenre();
        }
        return null;
    }
}
