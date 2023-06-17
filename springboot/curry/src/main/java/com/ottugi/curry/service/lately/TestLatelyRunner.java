package com.ottugi.curry.service.lately;

import com.ottugi.curry.domain.lately.Lately;
import com.ottugi.curry.domain.lately.LatelyRepository;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.service.bookmark.BookmarkServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
public class TestLatelyRunner implements ApplicationRunner {

    private final LatelyRepository latelyRepository;
    private final BookmarkServiceImpl bookmarkService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // 초기 최근 본 레시피 데이터 - 추후 삭제
        List<Long> recipeIdList = Arrays.asList(6842324L, 6845721L);

        User user = bookmarkService.findUser(1L);

        for (Long recipeId: recipeIdList) {
            Recipe recipe = bookmarkService.findRecipe(recipeId);

            Lately lately = new Lately();
            lately.setUser(user);
            lately.setRecipe(recipe);

            latelyRepository.save(lately);
        }
    }
}