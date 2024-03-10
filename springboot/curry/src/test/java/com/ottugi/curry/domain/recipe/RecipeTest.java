package com.ottugi.curry.domain.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RecipeTest {
    public static final Long ID = 12345L;
    public static final Long RECIPE_ID = 1L;
    public static final String NAME = "꿀고구마맛탕";
    public static final String THUMBNAIL = "thumbnail.png";
    public static final Time TIME = Time.SIXTY_MINUTES;
    public static final Difficulty DIFFICULTY = Difficulty.BEGINNER;
    public static final Composition COMPOSITION = Composition.LIGHTLY;
    public static final String INGREDIENT = "고구마";
    public static final String INGREDIENTS = "[재료] 고구마| 식용유";
    public static final Servings SERVINGS = Servings.TWO;
    public static final String ORDERS = "|1. |2. |3. ";
    public static final String PHOTO = "photo.jpg";
    public static final String GENRE = "ing13|ing21";
    public static final Boolean IS_FAVORITE_GENRE = true;

    public static final int PAGE = 1;
    public static final int SIZE = 1;

    public static Recipe initRecipe() {
        return Recipe.builder()
                .id(ID)
                .recipeId(RECIPE_ID)
                .name(NAME)
                .thumbnail(THUMBNAIL)
                .time(TIME)
                .difficulty(DIFFICULTY)
                .composition(COMPOSITION)
                .ingredients(INGREDIENTS)
                .servings(SERVINGS)
                .orders(ORDERS)
                .photo(PHOTO)
                .genre(GENRE)
                .build();
    }

    private Recipe recipe;

    @BeforeEach
    public void setUp() {
        recipe = initRecipe();
    }

    @Test
    @DisplayName("레시피 추가 테스트")
    void testRecipe() {
        assertNotNull(recipe);
        assertEquals(ID, recipe.getId());
        assertEquals(RECIPE_ID, recipe.getRecipeId());
        assertEquals(NAME, recipe.getName());
        assertEquals(THUMBNAIL, recipe.getThumbnail());
        assertEquals(TIME, recipe.getTime());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(COMPOSITION, recipe.getComposition());
        assertEquals(INGREDIENTS, recipe.getIngredients());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(ORDERS, recipe.getOrders());
        assertEquals(PHOTO, recipe.getPhoto());
        assertEquals(GENRE, recipe.getGenre());
    }
}