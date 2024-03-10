package com.ottugi.curry.domain.recipe;

import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.except.BaseCode;
import com.ottugi.curry.except.InvalidException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    VEGETABLE(List.of("ing13", "ing14", "ing15", "ing16", "ing17", "ing18"), "vegetable"),
    FRUIT(List.of("ing19", "ing20", "ing21", "ing22", "ing23", "ing24"), "vegetable"),
    MILK(List.of("ing25"), "egg");

    private final List<String> ingredientNumbers;
    private final String genreCharacter;

    private static final Map<String, Genre> GENRE_MAP = new HashMap<>();

    static {
        for (Genre genre : Genre.values()) {
            for (String ingredientNumber : genre.getIngredientNumbers()) {
                GENRE_MAP.put(ingredientNumber, genre);
            }
        }
    }

    public static Genre findByIngredientNumber(String ingredientNumber) {
        Genre foundGenre = GENRE_MAP.get(ingredientNumber);
        if (foundGenre == null) {
            throw new InvalidException(BaseCode.BAD_REQUEST);
        }
        return foundGenre;
    }

    public static String findMainGenreCharacter(Recipe recipe) {
        String[] genres = convertToGenreArray(recipe);
        if (genres.length > 0) {
            return findByIngredientNumber(genres[0]).getGenreCharacter();
        }
        return null;
    }

    public static Boolean containFavoriteGenre(Recipe recipe, User user) {
        String favoriteGenre = user.getFavoriteGenre();
        String[] genres = convertToGenreArray(recipe);
        return favoriteGenre != null && List.of(genres).contains(favoriteGenre);
    }

    private static String[] convertToGenreArray(Recipe recipe) {
        return recipe.getGenre().split("\\|");
    }
}