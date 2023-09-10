package com.ottugi.curry.service.bookmark;

import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.bookmark.BookmarkRepository;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.service.CommonService;
import com.ottugi.curry.web.dto.bookmark.BookmarkListResponseDto;
import com.ottugi.curry.web.dto.bookmark.BookmarkRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final CommonService commonService;

    // 북마크 추가 또는 제거
    @Override
    @Transactional
    public Boolean addOrRemoveBookmark(BookmarkRequestDto bookmarkRequestDto) {
        User user = commonService.findByUserId(bookmarkRequestDto.getUserId());
        Recipe recipe = commonService.findByRecipeId(bookmarkRequestDto.getRecipeId());

        if(commonService.isBookmarked(user, recipe)) {
            removeBookmark(user, recipe);
            return false;
        }
        else {
            addBookmark(user, recipe);
            return true;
        }
    }

    // 모든 북마크 목록 조회
    @Override
    @Transactional(readOnly = true)
    public Page<BookmarkListResponseDto> getBookmarkAll(Long userId, int page, int size) {
        User user = commonService.findByUserId(userId);
        List<Bookmark> bookmarkList = commonService.findBookmarkByUser(user);

        List<BookmarkListResponseDto> bookmarkListResponseDtoList = bookmarkList.stream()
                .map(bookmark -> new BookmarkListResponseDto(bookmark.getRecipeId(), true))
                .collect(Collectors.toList());

        return commonService.getPage(bookmarkListResponseDtoList, page, size);
    }

    // 북마크 검색
    @Override
    @Transactional(readOnly = true)
    public Page<BookmarkListResponseDto> searchBookmark(Long userId, int page, int size, String name, String time, String difficulty, String composition) {
        User user = commonService.findByUserId(userId);
        List<Bookmark> bookmarkList = commonService.findBookmarkByUser(user);

        List<BookmarkListResponseDto> bookmarkListResponseDtoList = new ArrayList<>();

        if (name == null) {
            name = "";
        }

        for (Bookmark bookmark : bookmarkList) {
            Recipe recipe = bookmark.getRecipeId();
            String recipeName = recipe.getName();

            if (recipeName.contains(name) && commonService.isRecipeMatching(recipe, time, difficulty, composition)) {
                bookmarkListResponseDtoList.add(new BookmarkListResponseDto(recipe, true));
            }
        }

        return commonService.getPage(bookmarkListResponseDtoList, page, size);
    }

    // 북마크 삭제
    @Transactional
    private void removeBookmark(User user, Recipe recipe) {
        Bookmark bookmark = commonService.findBookmarkByUserAndRecipe(user, recipe);
        bookmarkRepository.delete(bookmark);
    }

    // 북마크 추가
    @Transactional
    private void addBookmark(User user, Recipe recipe) {
        Bookmark bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setRecipe(recipe);
        bookmarkRepository.save(bookmark);
        user.addBookmarkList(bookmark);
    }
}
