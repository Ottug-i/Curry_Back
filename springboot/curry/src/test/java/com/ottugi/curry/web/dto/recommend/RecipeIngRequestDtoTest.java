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

public class RecipeIngRequestDtoTest {
    private final ValidatorUtil<RecipeIngRequestDto> validatorUtil = new ValidatorUtil<>();

    public static RecipeIngRequestDto initRecipeIngRequestDto(User user, Recipe recipe) {
        return RecipeIngRequestDto.builder()
                .userId(user.getId())
                .ingredients(List.of(INGREDIENT))
                .time(recipe.getTime().getTimeName())
                .difficulty(recipe.getDifficulty().getDifficulty())
                .composition(recipe.getComposition().getComposition())
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
    @DisplayName("RecipeIngRequestDto 생성 테스트")
    void testRecipeIngRequestDto() {
        RecipeIngRequestDto recipeIngRequestDto = initRecipeIngRequestDto(user, recipe);

        assertEquals(user.getId(), recipeIngRequestDto.getUserId());
        assertEquals(1, recipeIngRequestDto.getIngredients().size());
        assertEquals(INGREDIENT, recipeIngRequestDto.getIngredients().get(0));
        assertEquals(recipe.getTime().getTimeName(), recipeIngRequestDto.getTime());
        assertEquals(recipe.getDifficulty().getDifficulty(), recipeIngRequestDto.getDifficulty());
        assertEquals(recipe.getComposition().getComposition(), recipeIngRequestDto.getComposition());
        assertEquals(PAGE, recipeIngRequestDto.getPage());
        assertEquals(SIZE, recipeIngRequestDto.getSize());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        RecipeIngRequestDto recipeIngRequestDto = new RecipeIngRequestDto();

        assertNotNull(recipeIngRequestDto);
        assertNull(recipeIngRequestDto.getUserId());
        assertNull(recipeIngRequestDto.getIngredients());
        assertNull(recipeIngRequestDto.getTime());
        assertNull(recipeIngRequestDto.getDifficulty());
        assertNull(recipeIngRequestDto.getComposition());
        assertEquals(0, recipeIngRequestDto.getPage());
        assertEquals(0, recipeIngRequestDto.getSize());
    }

    @Test
    @DisplayName("회원 아이디 유효성 검증 테스트")
    void userId_validation() {
        RecipeIngRequestDto recipeIngRequestDto = RecipeIngRequestDto.builder()
                .userId(null)
                .ingredients(List.of(INGREDIENT))
                .time(recipe.getTime().getTimeName())
                .difficulty(recipe.getDifficulty().getDifficulty())
                .composition(recipe.getComposition().getComposition())
                .page(PAGE)
                .size(SIZE)
                .build();

        validatorUtil.validate(recipeIngRequestDto);
    }

    @Test
    @DisplayName("인식된 재료 유효성 검증 테스트")
    void ingredients_validation() {
        RecipeIngRequestDto recipeIngRequestDto = RecipeIngRequestDto.builder()
                .userId(user.getId())
                .ingredients(new ArrayList<>())
                .time(recipe.getTime().getTimeName())
                .difficulty(recipe.getDifficulty().getDifficulty())
                .composition(recipe.getComposition().getComposition())
                .page(PAGE)
                .size(SIZE)
                .build();

        validatorUtil.validate(recipeIngRequestDto);
    }

    @Test
    @DisplayName("레시피 시간 유효성 검증 테스트")
    void time_validation() {
        RecipeIngRequestDto recipeIngRequestDto = RecipeIngRequestDto.builder()
                .userId(user.getId())
                .ingredients(List.of(INGREDIENT))
                .time(INVALID_BLANK)
                .difficulty(recipe.getDifficulty().getDifficulty())
                .composition(recipe.getComposition().getComposition())
                .page(PAGE)
                .size(SIZE)
                .build();

        validatorUtil.validate(recipeIngRequestDto);
    }

    @Test
    @DisplayName("레시피 난이도 유효성 검증 테스트")
    void difficulty_validation() {
        RecipeIngRequestDto recipeIngRequestDto = RecipeIngRequestDto.builder()
                .userId(user.getId())
                .ingredients(List.of(INGREDIENT))
                .time(recipe.getTime().getTimeName())
                .difficulty(INVALID_BLANK)
                .composition(recipe.getComposition().getComposition())
                .page(PAGE)
                .size(SIZE)
                .build();

        validatorUtil.validate(recipeIngRequestDto);
    }

    @Test
    @DisplayName("레시피 구성 유효성 검증 테스트")
    void composition_validation() {
        RecipeIngRequestDto recipeIngRequestDto = RecipeIngRequestDto.builder()
                .userId(user.getId())
                .ingredients(List.of(INGREDIENT))
                .time(recipe.getTime().getTimeName())
                .difficulty(recipe.getDifficulty().getDifficulty())
                .composition(INVALID_BLANK)
                .page(PAGE)
                .size(SIZE)
                .build();

        validatorUtil.validate(recipeIngRequestDto);
    }

    @Test
    @DisplayName("페이지 번호 유효성 검증 테스트")
    void page_validation() {
        RecipeIngRequestDto recipeIngRequestDto = RecipeIngRequestDto.builder()
                .userId(user.getId())
                .ingredients(List.of(INGREDIENT))
                .time(recipe.getTime().getTimeName())
                .difficulty(recipe.getDifficulty().getDifficulty())
                .composition(recipe.getComposition().getComposition())
                .page(0)
                .size(SIZE)
                .build();

        validatorUtil.validate(recipeIngRequestDto);
    }

    @Test
    @DisplayName("페이지 사이즈 유효성 검증 테스트")
    void size_validation() {
        RecipeIngRequestDto recipeIngRequestDto = RecipeIngRequestDto.builder()
                .userId(user.getId())
                .ingredients(List.of(INGREDIENT))
                .time(recipe.getTime().getTimeName())
                .difficulty(recipe.getDifficulty().getDifficulty())
                .composition(recipe.getComposition().getComposition())
                .page(PAGE)
                .size(0)
                .build();

        validatorUtil.validate(recipeIngRequestDto);
    }
}