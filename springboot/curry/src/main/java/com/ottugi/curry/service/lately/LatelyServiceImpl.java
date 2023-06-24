package com.ottugi.curry.service.lately;

import com.ottugi.curry.domain.lately.Lately;
import com.ottugi.curry.domain.lately.LatelyRepository;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.web.dto.lately.LatelyListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LatelyServiceImpl implements LatelyService {

    private final LatelyRepository latelyRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    @Override
    public Boolean addLately(Long userId, Long recipeId) {

        User user = findUser(userId);
        Recipe recipe = findRecipe(recipeId);

        deleteDuplicatedLately(user, recipe);
        fullLately(user);

        Lately lately = new Lately();
        lately.setUser(user);
        lately.setRecipe(recipe);
        Lately saveLately = latelyRepository.save(lately);
        user.addLatelyList(saveLately);
        return true;
    }

    @Override
    public List<LatelyListResponseDto> getLatelyAll(Long userId) {

        User user = findUser(userId);
        List<Lately> latelyList = latelyRepository.findByUserIdOrderByIdDesc(user);
        return latelyList.stream().map(lately -> new LatelyListResponseDto(lately.getRecipeId())).collect(Collectors.toList());
    }

    public User findUser(Long userId) {

        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
    }

    public Recipe findRecipe(Long recipeId) {

        return recipeRepository.findByRecipeId(recipeId).orElseThrow(() -> new IllegalArgumentException("해당 레시피가 없습니다."));
    }

    public void deleteDuplicatedLately(User user, Recipe recipe) {

        if (latelyRepository.findByUserIdAndRecipeId(user, recipe) != null) {
            latelyRepository.delete(latelyRepository.findByUserIdAndRecipeId(user, recipe));
        }
    }

    public void fullLately(User user) {

        if (latelyRepository.countByUserId(user) == 5) {
            List<Lately> latelyList = latelyRepository.findByUserIdOrderByIdDesc(user);
            latelyRepository.delete(latelyList.get(4));
        }
    }
}
