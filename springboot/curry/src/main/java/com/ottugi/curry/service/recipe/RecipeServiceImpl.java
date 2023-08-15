package com.ottugi.curry.service.recipe;

import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.service.CommonService;
import com.ottugi.curry.service.lately.LatelyService;
import com.ottugi.curry.service.rank.RankService;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final LatelyService latelyService;
    private final RankService rankService;
    private final CommonService commonService;

    // 레시피 상세 조회
    @Override
    @Transactional
    public RecipeResponseDto getRecipeDetail(Long userId, Long recipeId) {
        User user = commonService.findByUserId(userId);
        Recipe recipe = commonService.findByRecipeId(recipeId);
        latelyService.addLately(userId, recipeId);
        return new RecipeResponseDto(recipe, commonService.isBookmarked(user, recipe));
    }

    // 레시피 검색
    @Override
    @Transactional(readOnly = true)
    public Page<RecipeListResponseDto> searchByBox(Long userId, int page, int size, String name, String time, String difficulty, String composition) {
        User user = commonService.findByUserId(userId);
        List<Recipe> recipeList = findByName(name);
        List<RecipeListResponseDto> recipeListResponseDtoList = new ArrayList<>();

        if (time.isBlank() && difficulty.isBlank() && composition.isBlank()) {
            recipeListResponseDtoList = recipeList.stream().map(recipe -> new RecipeListResponseDto(recipe, commonService.isBookmarked(user, recipe))).collect(Collectors.toList());
            if (!recipeListResponseDtoList.isEmpty()) {
                log.info("rank up");
                rankService.updateOrAddRank(name);
            }
        }

        else {
            for (Recipe recipe: recipeList) {
                if (time == null || time.isEmpty()) {
                    time = "2시간 이상";
                }
                if (commonService.isRecipeMatching(recipe, time, difficulty, composition)) {
                    recipeListResponseDtoList.add(new RecipeListResponseDto(recipe, commonService.isBookmarked(user, recipe)));
                }
            }
        }
        return commonService.getPage(recipeListResponseDtoList, page, size);
    }

    // 레시피 이름으로 조회
    @Transactional(readOnly = true)
    private List<Recipe> findByName(String name) {
        return recipeRepository.findByNameContaining(name);
    }
}
