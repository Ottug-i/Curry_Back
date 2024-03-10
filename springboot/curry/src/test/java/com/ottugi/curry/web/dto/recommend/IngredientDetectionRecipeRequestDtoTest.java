package com.ottugi.curry.web.dto.recommend;

import static com.ottugi.curry.domain.recipe.RecipeTest.INGREDIENT;
import static com.ottugi.curry.domain.recipe.RecipeTest.PAGE;
import static com.ottugi.curry.domain.recipe.RecipeTest.SIZE;
import static com.ottugi.curry.web.dto.auth.UserSaveRequestDtoTest.INVALID_BLANK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ottugi.curry.ValidatorUtil;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeTest;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IngredientDetectionRecipeRequestDtoTest {
    private final ValidatorUtil<IngredientDetectionRecipeRequestDto> validatorUtil = new ValidatorUtil<>();

    public static IngredientDetectionRecipeRequestDto initIngredientDetectionRecipeRequestDto(User user, Recipe recipe) {
        return IngredientDetectionRecipeRequestDto.builder()
                .userId(user.getId())
                .ingredients(List.of(INGREDIENT))
                .time(recipe.getTime().getTimeName())
                .difficulty(recipe.getDifficulty().getDifficultyName())
                .composition(recipe.getComposition().getCompositionName())
                .page(PAGE)
                .size(SIZE)
                .build();
    }

    private Recipe recipe;
    private User user;

    @BeforeEach
    public void setUp() {
        recipe = RecipeTest.initRecipe();
        user = UserTest.initUser();
    }

    @Test
    @DisplayName("IngredientDetectionRecipeRequestDto 생성 테스트")
    void testIngredientDetectionRecipeRequestDto() {
        IngredientDetectionRecipeRequestDto ingredientDetectionRecipeRequestDto = initIngredientDetectionRecipeRequestDto(user, recipe);

        assertEquals(user.getId(), ingredientDetectionRecipeRequestDto.getUserId());
        assertEquals(1, ingredientDetectionRecipeRequestDto.getIngredients().size());
        assertEquals(INGREDIENT, ingredientDetectionRecipeRequestDto.getIngredients().get(0));
        assertEquals(recipe.getTime().getTimeName(), ingredientDetectionRecipeRequestDto.getTime());
        assertEquals(recipe.getDifficulty().getDifficultyName(), ingredientDetectionRecipeRequestDto.getDifficulty());
        assertEquals(recipe.getComposition().getCompositionName(), ingredientDetectionRecipeRequestDto.getComposition());
        assertEquals(PAGE, ingredientDetectionRecipeRequestDto.getPage());
        assertEquals(SIZE, ingredientDetectionRecipeRequestDto.getSize());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        IngredientDetectionRecipeRequestDto ingredientDetectionRecipeRequestDto = new IngredientDetectionRecipeRequestDto();

        assertNotNull(ingredientDetectionRecipeRequestDto);
        assertNull(ingredientDetectionRecipeRequestDto.getUserId());
        assertNull(ingredientDetectionRecipeRequestDto.getIngredients());
        assertNull(ingredientDetectionRecipeRequestDto.getTime());
        assertNull(ingredientDetectionRecipeRequestDto.getDifficulty());
        assertNull(ingredientDetectionRecipeRequestDto.getComposition());
        assertEquals(0, ingredientDetectionRecipeRequestDto.getPage());
        assertEquals(0, ingredientDetectionRecipeRequestDto.getSize());
    }

    @Test
    @DisplayName("회원 아이디 유효성 검증 테스트")
    void userId_validation() {
        IngredientDetectionRecipeRequestDto ingredientDetectionRecipeRequestDto = IngredientDetectionRecipeRequestDto.builder()
                .userId(null)
                .ingredients(List.of(INGREDIENT))
                .time(recipe.getTime().getTimeName())
                .difficulty(recipe.getDifficulty().getDifficultyName())
                .composition(recipe.getComposition().getCompositionName())
                .page(PAGE)
                .size(SIZE)
                .build();

        validatorUtil.validate(ingredientDetectionRecipeRequestDto);
    }

    @Test
    @DisplayName("인식된 재료 유효성 검증 테스트")
    void ingredients_validation() {
        IngredientDetectionRecipeRequestDto ingredientDetectionRecipeRequestDto = IngredientDetectionRecipeRequestDto.builder()
                .userId(user.getId())
                .ingredients(new ArrayList<>())
                .time(recipe.getTime().getTimeName())
                .difficulty(recipe.getDifficulty().getDifficultyName())
                .composition(recipe.getComposition().getCompositionName())
                .page(PAGE)
                .size(SIZE)
                .build();

        validatorUtil.validate(ingredientDetectionRecipeRequestDto);
    }

    @Test
    @DisplayName("레시피 시간 유효성 검증 테스트")
    void time_validation() {
        IngredientDetectionRecipeRequestDto ingredientDetectionRecipeRequestDto = IngredientDetectionRecipeRequestDto.builder()
                .userId(user.getId())
                .ingredients(List.of(INGREDIENT))
                .time(INVALID_BLANK)
                .difficulty(recipe.getDifficulty().getDifficultyName())
                .composition(recipe.getComposition().getCompositionName())
                .page(PAGE)
                .size(SIZE)
                .build();

        validatorUtil.validate(ingredientDetectionRecipeRequestDto);
    }

    @Test
    @DisplayName("레시피 난이도 유효성 검증 테스트")
    void difficulty_validation() {
        IngredientDetectionRecipeRequestDto ingredientDetectionRecipeRequestDto = IngredientDetectionRecipeRequestDto.builder()
                .userId(user.getId())
                .ingredients(List.of(INGREDIENT))
                .time(recipe.getTime().getTimeName())
                .difficulty(INVALID_BLANK)
                .composition(recipe.getComposition().getCompositionName())
                .page(PAGE)
                .size(SIZE)
                .build();

        validatorUtil.validate(ingredientDetectionRecipeRequestDto);
    }

    @Test
    @DisplayName("레시피 구성 유효성 검증 테스트")
    void composition_validation() {
        IngredientDetectionRecipeRequestDto ingredientDetectionRecipeRequestDto = IngredientDetectionRecipeRequestDto.builder()
                .userId(user.getId())
                .ingredients(List.of(INGREDIENT))
                .time(recipe.getTime().getTimeName())
                .difficulty(recipe.getDifficulty().getDifficultyName())
                .composition(INVALID_BLANK)
                .page(PAGE)
                .size(SIZE)
                .build();

        validatorUtil.validate(ingredientDetectionRecipeRequestDto);
    }

    @Test
    @DisplayName("페이지 번호 유효성 검증 테스트")
    void page_validation() {
        IngredientDetectionRecipeRequestDto ingredientDetectionRecipeRequestDto = IngredientDetectionRecipeRequestDto.builder()
                .userId(user.getId())
                .ingredients(List.of(INGREDIENT))
                .time(recipe.getTime().getTimeName())
                .difficulty(recipe.getDifficulty().getDifficultyName())
                .composition(recipe.getComposition().getCompositionName())
                .page(0)
                .size(SIZE)
                .build();

        validatorUtil.validate(ingredientDetectionRecipeRequestDto);
    }

    @Test
    @DisplayName("페이지 사이즈 유효성 검증 테스트")
    void size_validation() {
        IngredientDetectionRecipeRequestDto ingredientDetectionRecipeRequestDto = IngredientDetectionRecipeRequestDto.builder()
                .userId(user.getId())
                .ingredients(List.of(INGREDIENT))
                .time(recipe.getTime().getTimeName())
                .difficulty(recipe.getDifficulty().getDifficultyName())
                .composition(recipe.getComposition().getCompositionName())
                .page(PAGE)
                .size(0)
                .build();

        validatorUtil.validate(ingredientDetectionRecipeRequestDto);
    }
}