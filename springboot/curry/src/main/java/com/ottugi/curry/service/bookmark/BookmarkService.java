package com.ottugi.curry.service.bookmark;

import com.ottugi.curry.web.dto.bookmark.BookmarkListResponseDto;
import com.ottugi.curry.web.dto.bookmark.BookmarkRequestDto;
import org.springframework.data.domain.Page;

public interface BookmarkService {
    Boolean addOrRemoveBookmark(BookmarkRequestDto bookmarkRequestDto);
    Page<BookmarkListResponseDto> getBookmarkAll(Long userId, int page, int size);
    Page<BookmarkListResponseDto> searchBookmark(Long userId, int page, int size, String name, String time, String difficulty, String composition);
}
