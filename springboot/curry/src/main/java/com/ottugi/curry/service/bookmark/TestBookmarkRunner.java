package com.ottugi.curry.service.bookmark;

import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.bookmark.BookmarkRepository;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
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
public class TestBookmarkRunner implements ApplicationRunner {

    private final BookmarkRepository bookmarkRepository;
    private final BookmarkServiceImpl bookmarkService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // 초기 북마크 데이터 - 추후 삭제
        List<Long> recipeIdList = Arrays.asList(6842324L, 6845721L, 6845906L, 6846020L, 6846262L);

        User user = bookmarkService.findUser(1L);

        for (Long recipeId: recipeIdList) {
            Recipe recipe = bookmarkService.findRecipe(recipeId);

            Bookmark bookmark = new Bookmark();
            bookmark.setUser(user);
            bookmark.setRecipe(recipe);
            bookmarkRepository.save(bookmark);
        }
    }
}
