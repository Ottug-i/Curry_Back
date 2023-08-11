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

    // 북마크 추가 또는 제거
    @Override
    public Boolean addOrRemoveBookmark(BookmarkRequestDto bookmarkRequestDto) {

        User user = findUser(bookmarkRequestDto.getUserId());
        Recipe recipe = findRecipe(bookmarkRequestDto.getRecipeId());

        if(isBookmarked(user, recipe)) {
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

    // 모든 북마크 목록 조회
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

    // 북마크 검색
    @Override
    public Page<BookmarkListResponseDto> searchBookmark(Long userId, int page, int size, String name, String time, String difficulty, String composition) {

        User user = findUser(userId);
        List<Bookmark> bookmarkList = bookmarkRepository.findByUserId(user);
        List<BookmarkListResponseDto> bookmarkListResponseDtoList = new ArrayList<>();

        if (name == null || name.isBlank()) {
            for (Bookmark bookmark : bookmarkList) {
                Recipe recipe = bookmark.getRecipeId();
                if (time == null || time.isEmpty()) {
                    time = "2시간 이상";
                }
                if (isRecipeMatching(recipe, time, difficulty, composition)) {
                    bookmarkListResponseDtoList.add(new BookmarkListResponseDto(recipe, true));
                }
            }
        } else if (time.isBlank() && difficulty.isBlank() && composition.isBlank()) {
            for (Bookmark bookmark : bookmarkList) {
                Recipe recipe = bookmark.getRecipeId();
                if (recipe.getName().contains(name)) {
                    bookmarkListResponseDtoList.add(new BookmarkListResponseDto(recipe, true));
                }
            }
        } else {
            for (Bookmark bookmark : bookmarkList) {
                Recipe recipe = bookmark.getRecipeId();
                if (time == null || time.isEmpty()) {
                    time = "2시간 이상";
                }
                if (recipe.getName().contains(name) && isRecipeMatching(recipe, time, difficulty, composition)) {
                    bookmarkListResponseDtoList.add(new BookmarkListResponseDto(recipe, true));
                }
            }
        }

        return getPage(bookmarkListResponseDtoList, page, size);
    }

    // 회원 조회
    public User findUser(Long userId) {

        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
    }

    // 레시피 조회
    public Recipe findRecipe(Long recipeId) {

        return recipeRepository.findByRecipeId(recipeId).orElseThrow(() -> new IllegalArgumentException("해당 레시피가 없습니다."));
    }

    // 북마크 여부
    public Boolean isBookmarked(User user, Recipe recipe) {

        return bookmarkRepository.findByUserIdAndRecipeId(user, recipe) != null;
    }

    // 레시피 조건 일치 확인
    public Boolean isRecipeMatching(Recipe recipe, String time, String difficulty, String composition) {
        return recipe.getTime().getTime() <= Time.ofTime(time).getTime() && recipe.getDifficulty().getDifficulty().contains(difficulty) && recipe.getComposition().getComposition().contains(composition);
    }

    // 페이지 처리
    public Page<BookmarkListResponseDto> getPage(List<BookmarkListResponseDto> bookmarkListResponseDtoList, int page, int size) {
        int totalItems = bookmarkListResponseDtoList.size();
        int fromIndex = Math.max(0, page - 1) * size;
        int toIndex = Math.min(totalItems, fromIndex + size);
        bookmarkListResponseDtoList = bookmarkListResponseDtoList.subList(fromIndex, toIndex);
        return new PageImpl<>(bookmarkListResponseDtoList, PageRequest.of(page - 1, size), totalItems);
    }
}
