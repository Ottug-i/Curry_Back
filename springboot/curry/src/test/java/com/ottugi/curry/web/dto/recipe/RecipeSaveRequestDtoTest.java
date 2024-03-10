package com.ottugi.curry.web.dto.recipe;

import static com.ottugi.curry.web.dto.auth.UserSaveRequestDtoTest.INVALID_BLANK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ottugi.curry.ValidatorUtil;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RecipeSaveRequestDtoTest {
    private final ValidatorUtil<RecipeSaveRequestDto> validatorUtil = new ValidatorUtil<>();

    private static RecipeSaveRequestDto initRecipeSaveRequestDto(Recipe recipe) {
        return RecipeSaveRequestDto.builder()
                .recipeId(recipe.getRecipeId())
                .name(recipe.getName())
                .composition(recipe.getComposition().getCompositionName())
                .ingredients(recipe.getIngredients())
                .servings(recipe.getServings().getServingName())
                .difficulty(recipe.getDifficulty().getDifficultyName())
                .thumbnail(recipe.getThumbnail())
                .time(recipe.getTime().getTimeName())
                .orders(recipe.getOrders())
                .photo(recipe.getPhoto())
                .genre(recipe.getGenre())
                .build();
    }

    private Recipe recipe;

    @BeforeEach
    public void setUp() {
        recipe = RecipeTest.initRecipe();
    }

    @Test
    @DisplayName("RecipeSaveRequestDto 생성 테스트")
    void testRecipeSaveRequestDto() {
        RecipeSaveRequestDto recipeSaveRequestDto = initRecipeSaveRequestDto(recipe);

        assertEquals(recipe.getRecipeId(), recipeSaveRequestDto.getRecipeId());
        assertEquals(recipe.getName(), recipeSaveRequestDto.getName());
        assertEquals(recipe.getComposition().getCompositionName(), recipeSaveRequestDto.getComposition());
        assertEquals(recipe.getIngredients(), recipeSaveRequestDto.getIngredients());
        assertEquals(recipe.getServings().getServingName(), recipeSaveRequestDto.getServings());
        assertEquals(recipe.getDifficulty().getDifficultyName(), recipeSaveRequestDto.getDifficulty());
        assertEquals(recipe.getThumbnail(), recipeSaveRequestDto.getThumbnail());
        assertEquals(recipe.getTime().getTimeName(), recipeSaveRequestDto.getTime());
        assertEquals(recipe.getOrders(), recipeSaveRequestDto.getOrders());
        assertEquals(recipe.getPhoto(), recipeSaveRequestDto.getPhoto());
        assertEquals(recipe.getGenre(), recipeSaveRequestDto.getGenre());
    }

    @Test
    @DisplayName("RecipeSaveRequestDto toEntity 생성 테스트")
    void testRecipeSaveRequestDtoEntity() {
        RecipeSaveRequestDto recipeSaveRequestDto = initRecipeSaveRequestDto(recipe);

        Recipe recipeEntity = recipeSaveRequestDto.toEntity();

        assertNotNull(recipeEntity);
        assertEquals(recipeSaveRequestDto.getRecipeId(), recipeEntity.getRecipeId());
        assertEquals(recipeSaveRequestDto.getName(), recipeEntity.getName());
        assertEquals(recipeSaveRequestDto.getComposition(), recipeEntity.getComposition().getCompositionName());
        assertEquals(recipeSaveRequestDto.getIngredients(), recipeEntity.getIngredients());
        assertEquals(recipeSaveRequestDto.getServings(), recipeEntity.getServings().getServingName());
        assertEquals(recipeSaveRequestDto.getDifficulty(), recipeEntity.getDifficulty().getDifficultyName());
        assertEquals(recipeSaveRequestDto.getThumbnail(), recipeEntity.getThumbnail());
        assertEquals(recipeSaveRequestDto.getTime(), recipeEntity.getTime().getTimeName());
        assertEquals(recipeSaveRequestDto.getOrders(), recipeEntity.getOrders());
        assertEquals(recipeSaveRequestDto.getPhoto(), recipeEntity.getPhoto());
        assertEquals(recipeSaveRequestDto.getGenre(), recipeEntity.getGenre());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        RecipeSaveRequestDto recipeSaveRequestDto = new RecipeSaveRequestDto();

        assertNotNull(recipeSaveRequestDto);
        assertNull(recipeSaveRequestDto.getRecipeId());
        assertNull(recipeSaveRequestDto.getName());
        assertNull(recipeSaveRequestDto.getComposition());
        assertNull(recipeSaveRequestDto.getIngredients());
        assertNull(recipeSaveRequestDto.getServings());
        assertNull(recipeSaveRequestDto.getDifficulty());
        assertNull(recipeSaveRequestDto.getThumbnail());
        assertNull(recipeSaveRequestDto.getTime());
        assertNull(recipeSaveRequestDto.getOrders());
        assertNull(recipeSaveRequestDto.getPhoto());
        assertNull(recipeSaveRequestDto.getGenre());
    }

    @Test
    @DisplayName("레시피 아이디 유효성 검증 테스트")
    void recipeId_validation() {
        RecipeSaveRequestDto recipeSaveRequestDto = RecipeSaveRequestDto.builder()
                .recipeId(null)
                .name(recipe.getName())
                .composition(recipe.getComposition().getCompositionName())
                .ingredients(recipe.getIngredients())
                .servings(recipe.getServings().getServingName())
                .difficulty(recipe.getDifficulty().getDifficultyName())
                .thumbnail(recipe.getThumbnail())
                .time(recipe.getTime().getTimeName())
                .orders(recipe.getOrders())
                .photo(recipe.getPhoto())
                .genre(recipe.getGenre())
                .build();

        validatorUtil.validate(recipeSaveRequestDto);
    }

    @Test
    @DisplayName("레시피 이름 유효성 검증 테스트")
    void name_validation() {
        RecipeSaveRequestDto recipeSaveRequestDto = RecipeSaveRequestDto.builder()
                .recipeId(recipe.getRecipeId())
                .name(INVALID_BLANK)
                .composition(recipe.getComposition().getCompositionName())
                .ingredients(recipe.getIngredients())
                .servings(recipe.getServings().getServingName())
                .difficulty(recipe.getDifficulty().getDifficultyName())
                .thumbnail(recipe.getThumbnail())
                .time(recipe.getTime().getTimeName())
                .orders(recipe.getOrders())
                .photo(recipe.getPhoto())
                .genre(recipe.getGenre())
                .build();

        validatorUtil.validate(recipeSaveRequestDto);
    }

    @Test
    @DisplayName("레시피 구성 유효성 검증 테스트")
    void composition_validation() {
        RecipeSaveRequestDto recipeSaveRequestDto = RecipeSaveRequestDto.builder()
                .recipeId(recipe.getRecipeId())
                .name(recipe.getName())
                .composition(INVALID_BLANK)
                .ingredients(recipe.getIngredients())
                .servings(recipe.getServings().getServingName())
                .difficulty(recipe.getDifficulty().getDifficultyName())
                .thumbnail(recipe.getThumbnail())
                .time(recipe.getTime().getTimeName())
                .orders(recipe.getOrders())
                .photo(recipe.getPhoto())
                .genre(recipe.getGenre())
                .build();

        validatorUtil.validate(recipeSaveRequestDto);
    }

    @Test
    @DisplayName("레시피 재료 유효성 검증 테스트")
    void ingredients_validation() {
        RecipeSaveRequestDto recipeSaveRequestDto = RecipeSaveRequestDto.builder()
                .recipeId(recipe.getRecipeId())
                .name(recipe.getName())
                .composition(recipe.getComposition().getCompositionName())
                .ingredients(INVALID_BLANK)
                .servings(recipe.getServings().getServingName())
                .difficulty(recipe.getDifficulty().getDifficultyName())
                .thumbnail(recipe.getThumbnail())
                .time(recipe.getTime().getTimeName())
                .orders(recipe.getOrders())
                .photo(recipe.getPhoto())
                .genre(recipe.getGenre())
                .build();

        validatorUtil.validate(recipeSaveRequestDto);
    }

    @Test
    @DisplayName("레시피 인분 유효성 검증 테스트")
    void servings_validation() {
        RecipeSaveRequestDto recipeSaveRequestDto = RecipeSaveRequestDto.builder()
                .recipeId(recipe.getRecipeId())
                .name(recipe.getName())
                .composition(recipe.getComposition().getCompositionName())
                .ingredients(recipe.getIngredients())
                .servings(INVALID_BLANK)
                .difficulty(recipe.getDifficulty().getDifficultyName())
                .thumbnail(recipe.getThumbnail())
                .time(recipe.getTime().getTimeName())
                .orders(recipe.getOrders())
                .photo(recipe.getPhoto())
                .genre(recipe.getGenre())
                .build();

        validatorUtil.validate(recipeSaveRequestDto);
    }

    @Test
    @DisplayName("레시피 난이도 유효성 검증 테스트")
    void difficulty_validation() {
        RecipeSaveRequestDto recipeSaveRequestDto = RecipeSaveRequestDto.builder()
                .recipeId(recipe.getRecipeId())
                .name(recipe.getName())
                .composition(recipe.getComposition().getCompositionName())
                .ingredients(recipe.getIngredients())
                .servings(recipe.getServings().getServingName())
                .difficulty(INVALID_BLANK)
                .thumbnail(recipe.getThumbnail())
                .time(recipe.getTime().getTimeName())
                .orders(recipe.getOrders())
                .photo(recipe.getPhoto())
                .genre(recipe.getGenre())
                .build();

        validatorUtil.validate(recipeSaveRequestDto);
    }

    @Test
    @DisplayName("레시피 썸네일 유효성 검증 테스트")
    void thumbnail_validation() {
        RecipeSaveRequestDto recipeSaveRequestDto = RecipeSaveRequestDto.builder()
                .recipeId(recipe.getRecipeId())
                .name(recipe.getName())
                .composition(recipe.getComposition().getCompositionName())
                .ingredients(recipe.getIngredients())
                .servings(recipe.getServings().getServingName())
                .difficulty(recipe.getDifficulty().getDifficultyName())
                .thumbnail(INVALID_BLANK)
                .time(recipe.getTime().getTimeName())
                .orders(recipe.getOrders())
                .photo(recipe.getPhoto())
                .genre(recipe.getGenre())
                .build();

        validatorUtil.validate(recipeSaveRequestDto);
    }

    @Test
    @DisplayName("레시피 시간 유효성 검증 테스트")
    void time_validation() {
        RecipeSaveRequestDto recipeSaveRequestDto = RecipeSaveRequestDto.builder()
                .recipeId(recipe.getRecipeId())
                .name(recipe.getName())
                .composition(recipe.getComposition().getCompositionName())
                .ingredients(recipe.getIngredients())
                .servings(recipe.getServings().getServingName())
                .difficulty(recipe.getDifficulty().getDifficultyName())
                .thumbnail(recipe.getThumbnail())
                .time(INVALID_BLANK)
                .orders(recipe.getOrders())
                .photo(recipe.getPhoto())
                .genre(recipe.getGenre())
                .build();

        validatorUtil.validate(recipeSaveRequestDto);
    }

    @Test
    @DisplayName("레시피 조리 순서 유효성 검증 테스트")
    void orders_validation() {
        RecipeSaveRequestDto recipeSaveRequestDto = RecipeSaveRequestDto.builder()
                .recipeId(recipe.getRecipeId())
                .name(recipe.getName())
                .composition(recipe.getComposition().getCompositionName())
                .ingredients(recipe.getIngredients())
                .servings(recipe.getServings().getServingName())
                .difficulty(recipe.getDifficulty().getDifficultyName())
                .thumbnail(recipe.getThumbnail())
                .time(recipe.getTime().getTimeName())
                .orders(INVALID_BLANK)
                .photo(recipe.getPhoto())
                .genre(recipe.getGenre())
                .build();

        validatorUtil.validate(recipeSaveRequestDto);
    }
}