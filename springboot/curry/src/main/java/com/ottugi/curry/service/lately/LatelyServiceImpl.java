package com.ottugi.curry.service.lately;

import com.ottugi.curry.domain.lately.Lately;
import com.ottugi.curry.domain.lately.LatelyRepository;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.service.CommonService;
import com.ottugi.curry.web.dto.lately.LatelyListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LatelyServiceImpl implements LatelyService {

    private final LatelyRepository latelyRepository;
    private final CommonService commonService;

    // 최근 본 레시피 목록 추가
    @Override
    @Transactional
    public Boolean addLately(Long userId, Long recipeId) {
        User user = commonService.findByUserId(userId);
        Recipe recipe = commonService.findByRecipeId(recipeId);

        removeDuplicatedLately(user, recipe);
        limitLatelySize(user);

        Lately lately = new Lately();
        lately.setUser(user);
        lately.setRecipe(recipe);
        latelyRepository.save(lately);
        return true;
    }

    // 최근에 본 레시피 목록 조회
    @Override
    @Transactional(readOnly = true)
    public List<LatelyListResponseDto> getLatelyAll(Long userId) {
        User user = commonService.findByUserId(userId);
        List<Lately> latelyList = findLatelyWithDescendingOrder(user);
        return latelyList.stream().map(lately -> new LatelyListResponseDto(lately.getRecipeId())).collect(Collectors.toList());
    }

    // 최근 본 레시피 목록에서 중복 레시피 제거
    private void removeDuplicatedLately(User user, Recipe recipe) {
        Lately duplicatedLately = latelyRepository.findByUserIdAndRecipeId(user, recipe);
        if (duplicatedLately != null) {
            latelyRepository.delete(duplicatedLately);
        }
    }

    // 최근 본 레시피 목록의 갯수를 5개로 제한
    private void limitLatelySize(User user) {
        if (latelyRepository.countByUserId(user) >= 5) {
            List<Lately> latelyList = findLatelyWithDescendingOrder(user);
            latelyRepository.delete(latelyList.get(4));
        }
    }

    // 최근에 본 레시피 순서 정렬 조회
    private List<Lately> findLatelyWithDescendingOrder(User user) {
        return latelyRepository.findByUserIdOrderByIdDesc(user);
    }
}
