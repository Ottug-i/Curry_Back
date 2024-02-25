package com.ottugi.curry.service.bookmark;

import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.bookmark.BookmarkRepository;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.service.PageUtil;
import com.ottugi.curry.service.recipe.RecipeService;
import com.ottugi.curry.service.user.UserService;
import com.ottugi.curry.web.dto.bookmark.BookmarkListResponseDto;
import com.ottugi.curry.web.dto.bookmark.BookmarkRequestDto;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final UserService userService;
    private final RecipeService recipeService;

    @Override
    public Boolean addOrRemoveBookmark(BookmarkRequestDto requestDto) {
        User user = userService.findUserByUserId(requestDto.getUserId());
        Recipe recipe = recipeService.findRecipeByRecipeId(requestDto.getRecipeId());
        if (isBookmarked(user, recipe)) {
            return deleteBookmark(user, recipe);
        }
        return createBookmark(user, recipe);
    }

    @Override
    public Page<BookmarkListResponseDto> findBookmarkPageByUserId(Long userId, int page, int size) {
        User user = userService.findUserByUserId(userId);
        List<BookmarkListResponseDto> bookmarkListResponseDtoList
                = user.getBookmarkList()
                .stream()
                .map(bookmark -> new BookmarkListResponseDto(bookmark.getRecipeId()))
                .collect(Collectors.toList());
        return PageUtil.convertResponseDtoPages(bookmarkListResponseDtoList, page, size);
    }

    @Override
    public Page<BookmarkListResponseDto> findBookmarkPageByOption(Long userId, int page, int size,
                                                                  String name, String time, String difficulty, String composition) {
        User user = userService.findUserByUserId(userId);
        List<BookmarkListResponseDto> bookmarkListResponseDtoList
                = user.getBookmarkList()
                .stream()
                .filter(bookmark -> bookmark.getRecipeId().getName().contains(Optional.ofNullable(name).orElse("")))
                .filter(bookmark -> recipeService.isRecipeMatching(bookmark.getRecipeId(), time, difficulty, composition))
                .map(bookmark -> new BookmarkListResponseDto(bookmark.getRecipeId()))
                .collect(Collectors.toList());
        return PageUtil.convertResponseDtoPages(bookmarkListResponseDtoList, page, size);
    }

    private Boolean isBookmarked(User user, Recipe recipe) {
        return bookmarkRepository.existsByUserIdAndRecipeId(user, recipe);
    }

    private boolean createBookmark(User user, Recipe recipe) {
        Bookmark bookmark = Bookmark.builder().build();
        bookmark.setUser(user);
        bookmark.setRecipe(recipe);
        user.addBookmarkList(bookmarkRepository.save(bookmark));
        return true;
    }

    private boolean deleteBookmark(User user, Recipe recipe) {
        bookmarkRepository.deleteByUserIdAndRecipeId(user, recipe);
        return false;
    }
}
