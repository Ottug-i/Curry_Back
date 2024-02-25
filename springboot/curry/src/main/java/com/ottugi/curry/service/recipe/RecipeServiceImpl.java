package com.ottugi.curry.service.recipe;

import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.domain.recipe.Time;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.except.BaseCode;
import com.ottugi.curry.except.NotFoundException;
import com.ottugi.curry.service.PageUtil;
import com.ottugi.curry.service.lately.LatelyService;
import com.ottugi.curry.service.rank.RankService;
import com.ottugi.curry.service.user.UserService;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeResponseDto;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserService userService;
    private final LatelyService latelyService;
    private final RankService rankService;

    @Override
    public RecipeResponseDto findRecipeByUserIdAndRecipeId(Long userId, Long recipeId) {
        User user = userService.findUserByUserId(userId);
        Recipe recipe = findRecipeByRecipeId(recipeId);
        latelyService.addLately(user, recipe);
        return new RecipeResponseDto(recipe, user);
    }

    @Override
    public Page<RecipeListResponseDto> findRecipePageBySearchBox(Long userId, int page, int size,
                                                                 String name, String time, String difficulty, String composition) {
        User user = userService.findUserByUserId(userId);
        List<Recipe> recipeList = recipeRepository.findByNameContaining(name);
        List<RecipeListResponseDto> recipeListResponseDtoList = recipeList.stream()
                .filter(filterPredicateForOptions(time, difficulty, composition))
                .map(recipe -> new RecipeListResponseDto(recipe, user))
                .collect(Collectors.toList());
        if (!recipeListResponseDtoList.isEmpty()) {
            rankService.updateOrAddRank(name);
        }
        return PageUtil.convertResponseDtoPages(recipeListResponseDtoList, page, size);
    }

    @Override
    public Recipe findRecipeByRecipeId(Long recipeId) {
        return recipeRepository.findByRecipeId(recipeId).orElseThrow(() -> new NotFoundException(BaseCode.RECIPE_NOT_FOUND));
    }

    @Override
    public List<Recipe> findRecipeListByRecipeIdIn(List<Long> recipeIdList) {
        return recipeRepository.findByRecipeIdIn(recipeIdList);
    }

    @Override
    public List<Recipe> findByRecipeListByIngredientsContaining(String ingredients) {
        return recipeRepository.findByIngredientsContaining(ingredients);
    }

    @Override
    public Predicate<Recipe> filterPredicateForOptions(String time, String difficulty, String composition) {
        if (time.isBlank() && difficulty.isBlank() && composition.isBlank()) {
            return recipe -> true;
        }
        return recipe -> isRecipeMatching(recipe, time, difficulty, composition);
    }

    @Override
    public Boolean isRecipeMatching(Recipe recipe, String time, String difficulty, String composition) {
        if (time == null || time.isEmpty()) {
            return isDifficultyMatching(recipe, difficulty) && isCompositionMatching(recipe, composition);
        }
        return isTimeMatching(recipe, time) && isDifficultyMatching(recipe, difficulty) && isCompositionMatching(recipe, composition);
    }

    private Boolean isDifficultyMatching(Recipe recipe, String difficulty) {
        return recipe.getDifficulty().getDifficulty().contains(difficulty);
    }

    private Boolean isCompositionMatching(Recipe recipe, String composition) {
        return recipe.getComposition().getComposition().contains(composition);
    }

    private Boolean isTimeMatching(Recipe recipe, String time) {
        if (time.equals(Time.TWO_HOURS.getTimeName())) {
            return recipe.getTime().getTimeName().contains(Time.TWO_HOURS.getTimeName());
        }
        return recipe.getTime().getTime() <= Time.ofTime(time).getTime();
    }
}
