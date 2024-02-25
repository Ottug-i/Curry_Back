package com.ottugi.curry.service.bookmark;

import com.ottugi.curry.web.dto.bookmark.BookmarkListResponseDto;
import com.ottugi.curry.web.dto.bookmark.BookmarkRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BookmarkService {
    Boolean addOrRemoveBookmark(BookmarkRequestDto requestDto);

    @Transactional(readOnly = true)
    Page<BookmarkListResponseDto> findBookmarkPageByUserId(Long userId, int page, int size);

    @Transactional(readOnly = true)
    Page<BookmarkListResponseDto> findBookmarkPageByOption(Long userId, int page, int size,
                                                           String name, String time, String difficulty, String composition);
}
