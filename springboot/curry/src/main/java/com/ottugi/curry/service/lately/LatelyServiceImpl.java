package com.ottugi.curry.service.lately;

import com.ottugi.curry.domain.lately.Lately;
import com.ottugi.curry.domain.lately.LatelyRepository;
import com.ottugi.curry.domain.recipe.Genre;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.except.BaseCode;
import com.ottugi.curry.except.NotFoundException;
import com.ottugi.curry.service.user.UserService;
import com.ottugi.curry.web.dto.lately.LatelyListResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LatelyServiceImpl implements LatelyService {
    private static final int LATELY_SIZE = 5;

    private final LatelyRepository latelyRepository;
    private final UserService userService;

    @Override
    public void addLately(User user, Recipe recipe) {
        removeDuplicatedLately(user, recipe);
        limitLatelySize(user);
        createLately(user, recipe);
    }

    @Override
    public List<LatelyListResponseDto> findLatelyListByUserId(Long userId) {
        User user = userService.findUserByUserId(userId);
        return user.getLatelyList()
                .stream()
                .map(LatelyListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public String findLatelyMainGenreCharacterFor3DCharacter(Long userId) {
        User user = userService.findUserByUserId(userId);
        Lately lately = findLatestLatelyByUser(user);
        if (lately != null) {
            return Genre.findMainGenreCharacter(lately.getRecipeId());
        }
        throw new NotFoundException(BaseCode.GENRE_NOT_FOUND);
    }

    private void removeDuplicatedLately(User user, Recipe recipe) {
        user.getLatelyList().removeIf(lately -> lately.getRecipeId().equals(recipe));
    }

    private void limitLatelySize(User user) {
        List<Lately> latelyList = user.getLatelyList();
        if (latelyList.size() >= LATELY_SIZE) {
            latelyList.remove(0);
        }
    }

    private void createLately(User user, Recipe recipe) {
        Lately lately = Lately.builder().build();
        lately.setUser(user);
        lately.setRecipe(recipe);
        latelyRepository.save(lately);
        user.addLatelyList(lately);
    }

    private Lately findLatestLatelyByUser(User user) {
        return latelyRepository.findTop1ByUserIdOrderByIdDesc(user);
    }
}
