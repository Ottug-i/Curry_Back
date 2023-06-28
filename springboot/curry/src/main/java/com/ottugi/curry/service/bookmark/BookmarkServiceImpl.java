package com.ottugi.curry.service.bookmark;

import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.bookmark.BookmarkRepository;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.domain.recipe.Time;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.web.dto.bookmark.BookmarkListResponseDto;
import com.ottugi.curry.web.dto.bookmark.BookmarkRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    @Override
    public Boolean addOrRemoveBookmark(BookmarkRequestDto bookmarkRequestDto) {

        User user = findUser(bookmarkRequestDto.getUserId());
        Recipe recipe = findRecipe(bookmarkRequestDto.getRecipeId());

        if(isBookmark(user, recipe)) {
            bookmarkRepository.delete(bookmarkRepository.findByUserIdAndRecipeId(user, recipe));
            return false;
        }
        else {
            Bookmark bookmark = new Bookmark();
            bookmark.setUser(user);
            bookmark.setRecipe(recipe);
            Bookmark saveBookmark = bookmarkRepository.save(bookmark);
            user.addBookmarkList(saveBookmark);
            return true;
        }
    }

    @Override
    public Page<BookmarkListResponseDto> getBookmarkAll(Long userId, int page, int size) {

        User user = findUser(userId);
        List<Bookmark> bookmarkList = bookmarkRepository.findByUserId(user);
        List<BookmarkListResponseDto> bookmarkListResponseDtoList = new ArrayList<>();
        for (Bookmark bookmark : bookmarkList) {
            bookmarkListResponseDtoList.add(new BookmarkListResponseDto(bookmark.getRecipeId(), true));
        }

        return getPage(bookmarkListResponseDtoList, page, size);
    }

    @Override
    public Page<BookmarkListResponseDto> searchByName(Long userId, int page, int size, String name) {

        User user = findUser(userId);
        List<Bookmark> bookmarkList = bookmarkRepository.findByUserId(user);
        List<BookmarkListResponseDto> bookmarkListResponseDtoList = new ArrayList<>();
        for (Bookmark bookmark : bookmarkList) {
            Recipe recipe = bookmark.getRecipeId();
            if (recipe.getName().contains(name)) {
                bookmarkListResponseDtoList.add(new BookmarkListResponseDto(recipe, true));
            }
        }

        return getPage(bookmarkListResponseDtoList, page, size);
    }

    @Override
    public Page<BookmarkListResponseDto> searchByOption(Long userId, int page, int size, String time, String difficulty, String composition) {

        User user = findUser(userId);
        List<Bookmark> bookmarkList = bookmarkRepository.findByUserId(user);
        List<BookmarkListResponseDto> bookmarkListResponseDtoList = new ArrayList<>();
        for (Bookmark bookmark : bookmarkList) {
            Recipe recipe = bookmark.getRecipeId();
            if (time == null || time.isEmpty()) {
                time = "2시간 이상";
            }
            if (isRecipeMatching(recipe, time, difficulty, composition)) {
                bookmarkListResponseDtoList.add(new BookmarkListResponseDto(recipe, true));
            }
        }

        return getPage(bookmarkListResponseDtoList, page, size);
    }

    public User findUser(Long userId) {

        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
    }

    public Recipe findRecipe(Long recipeId) {

        return recipeRepository.findByRecipeId(recipeId).orElseThrow(() -> new IllegalArgumentException("해당 레시피가 없습니다."));
    }

    public Boolean isBookmark(User user, Recipe recipe) {

        return bookmarkRepository.findByUserIdAndRecipeId(user, recipe) != null;
    }

    public Boolean isRecipeMatching(Recipe recipe, String time, String difficulty, String composition) {
        return recipe.getTime().getTime() <= Time.ofTime(time).getTime() && recipe.getDifficulty().getDifficulty().contains(difficulty) && recipe.getComposition().getComposition().contains(composition);
    }

    public Page<BookmarkListResponseDto> getPage(List<BookmarkListResponseDto> bookmarkListResponseDtoList, int page, int size) {
        int totalItems = bookmarkListResponseDtoList.size();
        int fromIndex = Math.max(0, page - 1) * size;
        int toIndex = Math.min(totalItems, fromIndex + size);
        bookmarkListResponseDtoList = bookmarkListResponseDtoList.subList(fromIndex, toIndex);
        return new PageImpl<>(bookmarkListResponseDtoList, PageRequest.of(page - 1, size), totalItems);
    }
}
