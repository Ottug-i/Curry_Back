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
import com.ottugi.curry.web.dto.recipe.RecipeRequestDto;
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

    @Override
    public Page<RecipeListResponseDto> getRecipeList(RecipeRequestDto recipeRequestDto) {

        List<Recipe> recipeList = recipeRepository.findByIngredientsContaining(recipeRequestDto.getIngredients().get(0));
        List<Recipe> resultList = new ArrayList<>();
        for (Recipe recipe: recipeList) {
            boolean isAllIncluded = true;
            for (int i = 1; i < recipeRequestDto.getIngredients().size(); i++) {
                if (!recipe.getIngredients().contains(recipeRequestDto.getIngredients().get(i))) {
                    isAllIncluded = false;
                    break;
                }
            }
            if (isAllIncluded) {
                resultList.add(recipe);
            }
        }

        int totalItems = resultList.size();
        int fromIndex = Math.max(0, (recipeRequestDto.getPage() - 1) * recipeRequestDto.getSize());
        int toIndex = Math.min(totalItems, fromIndex + recipeRequestDto.getSize());

        List<RecipeListResponseDto> pagedRecipeList = resultList.subList(fromIndex, toIndex)
                .stream()
                .map(recipe -> new RecipeListResponseDto(recipe, checkBookmark(recipeRequestDto.getUserId(), recipe.getRecipeId())))
                .collect(Collectors.toList());

        return new PageImpl<>(pagedRecipeList, PageRequest.of(recipeRequestDto.getPage() - 1, recipeRequestDto.getSize()), totalItems);
    }

    @Override
    public RecipeResponseDto getRecipeDetail(Long userId, Long recipeId) {

        Recipe recipe = recipeRepository.findByRecipeId(recipeId).orElseThrow(() -> new IllegalArgumentException("해당 레시피가 없습니다."));
        latelyService.addLately(userId, recipeId);
        return new RecipeResponseDto(recipe, checkBookmark(userId, recipeId));
    }

    @Override
    public Page<RecipeListResponseDto> searchByBox(Long userId, int page, int size, String name, String time, String difficulty, String composition) {

        List<Recipe> recipeList = recipeRepository.findByNameContaining(name);
        List<RecipeListResponseDto> recipeListResponseDtoList = new ArrayList<>();

        int totalItems = recipeList.size();
        int fromIndex = Math.max(0, page - 1) * size;
        int toIndex = Math.min(totalItems, fromIndex + size);

        if (time.isBlank() && difficulty.isBlank() && composition.isBlank()) {
            rankService.addRank(name);
            recipeListResponseDtoList = recipeList.subList(fromIndex, toIndex)
                    .stream().map(recipe -> new RecipeListResponseDto(recipe, checkBookmark(userId, recipe.getRecipeId()))).collect(Collectors.toList());
        }

        else {
            for (Recipe recipe: recipeList) {
                if (time == null || time.isEmpty()) {
                    time = "2시간 이상";
                }
                if (isRecipeMatching(recipe, time, difficulty, composition)) {
                    recipeListResponseDtoList.add(new RecipeListResponseDto(recipe, checkBookmark(userId, recipe.getRecipeId())));
                }
            }

            totalItems = recipeListResponseDtoList.size();
            toIndex = Math.min(totalItems, fromIndex + size);

            recipeListResponseDtoList = recipeListResponseDtoList.subList(fromIndex, toIndex);
        }
        return new PageImpl<>(recipeListResponseDtoList, PageRequest.of(page - 1, size), totalItems);
    }

    public Boolean checkBookmark(Long userId, Long recipeId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
        Recipe recipe = recipeRepository.findByRecipeId(recipeId).orElse(null);
        return bookmarkRepository.findByUserIdAndRecipeId(user, recipe) != null;
    }

    public Boolean isRecipeMatching(Recipe recipe, String time, String difficulty, String composition) {
        return recipe.getTime().getTime() <= Time.ofTime(time).getTime() && recipe.getDifficulty().getDifficulty().contains(difficulty) && recipe.getComposition().getComposition().contains(composition);
    }
}
