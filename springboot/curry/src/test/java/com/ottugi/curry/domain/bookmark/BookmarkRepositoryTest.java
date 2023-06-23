package com.ottugi.curry.domain.bookmark;

import com.ottugi.curry.domain.recipe.*;
import com.ottugi.curry.domain.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class BookmarkRepositoryTest {

    private final String email = "wn8925@sookmyung.ac.kr";
    private final String nickName = "가경";

    private final Long recipeId = 6842324L;
    private final String name = "고구마맛탕";
    private final String thumbnail = "https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/828bccf4fdd0a71b6477a8e96e84906b1.png";
    private final Time time = Time.ofTime("60분 이내");
    private final Difficulty difficulty = Difficulty.ofDifficulty("초급");
    private final Composition composition = Composition.ofComposition("가볍게");
    private final String ingredients = "[재료] 고구마| 식용유| 황설탕| 올리고당| 견과류| 물";
    private final Servings servings = Servings.ofServings("2인분");
    private final String orders = "|1. 바삭하게 튀기는 꿀팁|2. 달콤한 소스 꿀팁|3. 더 건강하게 먹는 꿀팁";
    private final String photo = "|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/4c9918cf77a109d28b389e6bc753b4bd1.jpg|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/66e8c5f5932e195e7b5405d110a6e67e1.jpg|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/8628264d141fa54487461d41a45d905f1.jpg";

    private User user;
    private Recipe recipe;
    private Bookmark bookmark;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void setUp() {

        // given
        user = User.builder().email(email).nickName(nickName).build();
        recipe = Recipe.builder()
                .id(recipeId)
                .name(name)
                .thumbnail(thumbnail)
                .time(time)
                .difficulty(difficulty)
                .composition(composition)
                .ingredients(ingredients)
                .servings(servings)
                .orders(orders)
                .photo(photo)
                .build();
        entityManager.persist(user);
        entityManager.persist(recipe);

        bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setRecipe(recipe);
        bookmarkRepository.save(bookmark);
    }

    @AfterEach
    void clean() {
        bookmarkRepository.deleteAll();
    }

    @Test
    void 북마크추가() {

        // when
        Bookmark findBookmark = bookmarkRepository.findByUserIdAndRecipeId(user, recipe);

        // then
        assertEquals(findBookmark, bookmark);
    }

    @Test
    void 북마크유저이름과레시피아이디로조회() {

        // when
        Bookmark findBookmark = bookmarkRepository.findByUserIdAndRecipeId(user, recipe);

        // then
        assertEquals(findBookmark.getUserId(), user);
        assertEquals(findBookmark.getRecipeId(), recipe);
    }

    @Test
    void 북마크유저이름으로리스트조회() {

        // when
        List<Bookmark> bookmarkList = bookmarkRepository.findByUserId(user);

        // then
        Bookmark findBookmark = bookmarkList.get(0);
        assertEquals(findBookmark.getUserId(), user);
        assertEquals(findBookmark.getRecipeId(), recipe);
    }
}