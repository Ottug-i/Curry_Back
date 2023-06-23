package com.ottugi.curry.service.bookmark;

import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.bookmark.BookmarkRepository;
import com.ottugi.curry.domain.recipe.*;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.web.dto.bookmark.BookmarkListResponseDto;
import com.ottugi.curry.web.dto.bookmark.BookmarkRequestDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookmarkServiceTest {

    private final Long userId = 1L;
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

    Long bookmarkId = 1L;

    @Mock
    private BookmarkRepository bookmarkRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private BookmarkServiceImpl bookmarkService;

    @Test
    void 북마크추가() {

        // given
        User user = new User(userId, email, nickName);
        Recipe recipe = new Recipe(recipeId, name, thumbnail, time, difficulty, composition, ingredients, servings, orders, photo);
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto(userId, recipeId);

        // when
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        when(recipeRepository.findById(recipeId)).thenReturn(java.util.Optional.of(recipe));
        when(bookmarkRepository.findByUserIdAndRecipeId(user, recipe)).thenReturn(null);
        when(bookmarkRepository.save(any())).thenReturn(new Bookmark());
        Boolean result = bookmarkService.addOrRemoveBookmark(bookmarkRequestDto);

        // then
        assertTrue(result);
    }

    @Test
    void 북마크삭제() {

        // given
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto(userId, recipeId);
        User user = new User(userId, email, nickName);
        Recipe recipe = new Recipe(recipeId, name, thumbnail, time, difficulty, composition, ingredients, servings, orders, photo);
        Bookmark bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setRecipe(recipe);

        // when
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        when(recipeRepository.findById(recipeId)).thenReturn(java.util.Optional.of(recipe));
        when(bookmarkRepository.findByUserIdAndRecipeId(user, recipe)).thenReturn(bookmark);
        Boolean result = bookmarkService.addOrRemoveBookmark(bookmarkRequestDto);

        // then
        assertFalse(result);
    }

    @Test
    void 북마크리스트조회() {

        // given
        User user = new User(userId, email, nickName);
        Recipe recipe = new Recipe(recipeId, name, thumbnail, time, difficulty, composition, ingredients, servings, orders, photo);
        Bookmark bookmark = new Bookmark(bookmarkId);
        bookmark.setUser(user);
        bookmark.setRecipe(recipe);

        // when
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        List<Bookmark> bookmarkList = new ArrayList<>();
        bookmarkList.add(bookmark);
        when(bookmarkRepository.findByUserId(user)).thenReturn(bookmarkList);
        List<BookmarkListResponseDto> bookmarkListResponseDtoList = bookmarkService.getBookmarkAll(userId);

        // then
        assertEquals(bookmarkListResponseDtoList.get(0).getName(), recipe.getName());
    }

    @Test
    void 이름으로북마크조회() {

        // given
        User user = new User(userId, email, nickName);
        Recipe recipe = new Recipe(recipeId, name, thumbnail, time, difficulty, composition, ingredients, servings, orders, photo);
        Bookmark bookmark = new Bookmark(bookmarkId);
        bookmark.setUser(user);
        bookmark.setRecipe(recipe);

        // when
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        List<Bookmark> bookmarkList = new ArrayList<>();
        bookmarkList.add(bookmark);
        when(bookmarkRepository.findByUserId(user)).thenReturn(bookmarkList);
        List<BookmarkListResponseDto> bookmarkListResponseDtoList = bookmarkService.searchByName(userId, recipe.getName());

        // then
        assertEquals(bookmarkListResponseDtoList.get(0).getName(), recipe.getName());
    }

    @Test
    void 옵션으로북마크조회() {

        // given
        User user = new User(userId, email, nickName);
        Recipe recipe = new Recipe(recipeId, name, thumbnail, time, difficulty, composition, ingredients, servings, orders, photo);
        Bookmark bookmark = new Bookmark(bookmarkId);
        bookmark.setUser(user);
        bookmark.setRecipe(recipe);

        // when
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        List<Bookmark> bookmarkList = new ArrayList<>();
        bookmarkList.add(bookmark);
        when(bookmarkRepository.findByUserId(user)).thenReturn(bookmarkList);
        List<BookmarkListResponseDto> bookmarkListResponseDtoList = bookmarkService.searchByOption(userId, recipe.getTime().toString(), recipe.getDifficulty().toString(), recipe.getComposition().toString());

        // then
        assertEquals(bookmarkListResponseDtoList.get(0).getName(), recipe.getName());
    }

}