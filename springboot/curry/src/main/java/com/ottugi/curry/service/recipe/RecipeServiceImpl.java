package com.ottugi.curry.service.recipe;

import com.ottugi.curry.domain.bookmark.BookmarkRepository;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.domain.recipe.Time;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.service.lately.LatelyService;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeRequestDto;
import com.ottugi.curry.web.dto.recipe.RecipeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;

    private final LatelyService latelyService;

    @Override
    public List<RecipeListResponseDto> getRecipeList(RecipeRequestDto recipeRequestDto) {

        List<Recipe> recipeList = recipeRepository.findByIdIn(recipeRequestDto.getRecipeId());
        if (recipeList.size() != recipeRequestDto.getRecipeId().size()) {
            throw new IllegalArgumentException("해당 레시피가 없습니다.");
        }
        return recipeList.stream().map(recipe -> new RecipeListResponseDto(recipe, checkBookmark(recipeRequestDto.getUserId(), recipe.getId()))).collect(Collectors.toList());
    }

    @Override
    public RecipeResponseDto getRecipeDetail(Long userId, Long recipeId) {

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new IllegalArgumentException("해당 레시피가 없습니다."));
        latelyService.addLately(userId, recipeId);
        return new RecipeResponseDto(recipe, checkBookmark(userId, recipeId));
    }

    @Override
    public List<RecipeListResponseDto> searchByBox(Long userId, String name, String time, String difficulty, String composition) {

        List<Recipe> recipeList = recipeRepository.findByName(name);
        List<RecipeListResponseDto> recipeListResponseDtoList = new ArrayList<>();

        if (time == null && difficulty == null && composition == null) {
            return recipeList.stream().map(recipe -> new RecipeListResponseDto(recipe, checkBookmark(userId, recipe.getId()))).collect(Collectors.toList());
        }

        else {
            for (Recipe recipe: recipeList) {
                if (time.isEmpty()) {
                    time = "2시간 이상";
                }
                if (isRecipeMatching(recipe, time, difficulty, composition)) {
                    recipeListResponseDtoList.add(new RecipeListResponseDto(recipe, checkBookmark(userId, recipe.getId())));
                }
            }
            return recipeListResponseDtoList;
        }
    }

    public Boolean checkBookmark(Long userId, Long recipeId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new IllegalArgumentException("해당 레시피가 없습니다."));
        return bookmarkRepository.findByUserIdAndRecipeId(user, recipe) != null;
    }

    public Boolean isRecipeMatching(Recipe recipe, String time, String difficulty, String composition) {
        return recipe.getTime().getTime() <= Time.ofTime(time).getTime() && recipe.getDifficulty().getDifficulty().contains(difficulty) && recipe.getComposition().getComposition().contains(composition);
    }

}
