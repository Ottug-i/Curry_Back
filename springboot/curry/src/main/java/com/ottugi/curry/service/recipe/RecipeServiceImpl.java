package com.ottugi.curry.service.recipe;

import com.ottugi.curry.domain.bookmark.BookmarkRepository;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.domain.recipe.Time;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.service.lately.LatelyService;
import com.ottugi.curry.service.rank.RankService;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final LatelyService latelyService;
    private final RankService rankService;

    // 레시피 상세 조회
    @Override
    public RecipeResponseDto getRecipeDetail(Long userId, Long recipeId) {

        Recipe recipe = recipeRepository.findByRecipeId(recipeId).orElseThrow(() -> new IllegalArgumentException("해당 레시피가 없습니다."));
        latelyService.addLately(userId, recipeId);
        return new RecipeResponseDto(recipe, isBookmarked(userId, recipeId));
    }

    // 레시피 검색
    @Override
    public Page<RecipeListResponseDto> searchByBox(Long userId, int page, int size, String name, String time, String difficulty, String composition) {

        List<Recipe> recipeList = recipeRepository.findByNameContaining(name);
        List<RecipeListResponseDto> recipeListResponseDtoList = new ArrayList<>();

        int totalItems = recipeList.size();
        int fromIndex = Math.max(0, page - 1) * size;
        int toIndex = Math.min(totalItems, fromIndex + size);

        if (time.isBlank() && difficulty.isBlank() && composition.isBlank()) {
            recipeListResponseDtoList = recipeList.subList(fromIndex, toIndex)
                    .stream().map(recipe -> new RecipeListResponseDto(recipe, isBookmarked(userId, recipe.getRecipeId()))).collect(Collectors.toList());
            if (recipeListResponseDtoList.size() != 0) {
                rankService.updateOrAddRank(name);
            }
        }

        else {
            for (Recipe recipe: recipeList) {
                if (time == null || time.isEmpty()) {
                    time = "2시간 이상";
                }
                if (isRecipeMatching(recipe, time, difficulty, composition)) {
                    recipeListResponseDtoList.add(new RecipeListResponseDto(recipe, isBookmarked(userId, recipe.getRecipeId())));
                }
            }

            totalItems = recipeListResponseDtoList.size();
            toIndex = Math.min(totalItems, fromIndex + size);

            recipeListResponseDtoList = recipeListResponseDtoList.subList(fromIndex, toIndex);
        }
        return new PageImpl<>(recipeListResponseDtoList, PageRequest.of(page - 1, size), totalItems);
    }

    // 북마크 여부
    public Boolean isBookmarked(Long userId, Long recipeId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
        Recipe recipe = recipeRepository.findByRecipeId(recipeId).orElse(null);
        return bookmarkRepository.findByUserIdAndRecipeId(user, recipe) != null;
    }

    // 레시피 조건 일치
    public Boolean isRecipeMatching(Recipe recipe, String time, String difficulty, String composition) {
        return recipe.getTime().getTime() <= Time.ofTime(time).getTime() && recipe.getDifficulty().getDifficulty().contains(difficulty) && recipe.getComposition().getComposition().contains(composition);
    }
}
