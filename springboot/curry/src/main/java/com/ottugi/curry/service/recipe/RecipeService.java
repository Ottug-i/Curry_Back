package com.ottugi.curry.service.recipe;

import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeResponseDto;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RecipeService {
    /**
     * 레시피를 상세 조회한다.
     *
     * @param userId   (회원 아이디)
     * @param recipeId (레시피 아이디)
     * @return RecipeResponseDto (레시피 정보를 담은 DTO)
     */
    @Transactional
    RecipeResponseDto findRecipeByUserIdAndRecipeId(Long userId, Long recipeId);

    /**
     * 레시피 목록 중 옵션으로 필터링하여 레시피 목록 페이지를 조회한다.
     *
     * @param userId      (회원 아이디)
     * @param page        (페이지 번호)
     * @param size        (페이지 크기)
     * @param name        (레시피 이름)
     * @param time        (레시피 시간)
     * @param difficulty  (레시피 난이도)
     * @param composition (레시피 구성)
     * @return Page<RecipeListResponseDto> (필터링된 레시피 목록 정보를 담은 DTO 페이지)
     */
    Page<RecipeListResponseDto> findRecipePageBySearchBox(Long userId, int page, int size,
                                                          String name, String time, String difficulty, String composition);

    /**
     * 레시피 아이디에 따른 레시피를 조회한다.
     *
     * @param recipeId (레시피 아이디)
     * @return Recipe (레시피)
     */
    Recipe findRecipeByRecipeId(Long recipeId);

    /**
     * 레시피 아이디 리스트에 따른 레시피 목록을 조회한다.
     *
     * @param recipeIdList (레시피 아이디 리스트)
     * @return List<Recipe> (레시피 목록)
     */
    List<Recipe> findRecipeListByRecipeIdIn(List<Long> recipeIdList);

    /**
     * 레시피 재료가 포함된 레시피 목록을 조회한다.
     *
     * @param ingredients (레시피 재료)
     * @return List<Recipe> (레시피 목록)
     */
    List<Recipe> findByRecipeListByIngredientsContaining(String ingredients);

    /**
     * 옵션 선택에 따른 레시피 조건을 조회한다.
     *
     * @param time        (레시피 시간)
     * @param difficulty  (레시피 난이도)
     * @param composition (레시피 구성)
     * @return Predicate<Recipe> (time, difficulty, composition이 모두 비어있을 경우 true, 아니라면 isRecipeMatchedCriteria 조건)
     */
    Predicate<Recipe> filterPredicateForOptions(String time, String difficulty, String composition);

    /**
     * 레시피 옵션에 따른 조건 충족 여부를 확인한다.
     *
     * @param recipe      (레시피)
     * @param time        (레시피 시간)
     * @param difficulty  (레시피 난이도)
     * @param composition (레시피 구성)
     * @return boolean (조건 충족 시 true, 아닐 경우 false)
     */
    boolean isRecipeMatchedCriteria(Recipe recipe, String time, String difficulty, String composition);

    /**
     * 회원과 레시피에 따른 레시피 북마크 여부를 조회한다.
     *
     * @param user   (회원)
     * @param recipe (레시피)
     * @return boolean (북마크 되어 있을 경우 true, 아닐 경우 false)
     */
    boolean isRecipeBookmarked(User user, Recipe recipe);
}
