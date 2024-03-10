package com.ottugi.curry.service.bookmark;

import com.ottugi.curry.web.dto.bookmark.BookmarkListResponseDto;
import com.ottugi.curry.web.dto.bookmark.BookmarkRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BookmarkService {
    /**
     * 북마크를 추가 또는 제거한다.
     *
     * @param requestDto (북마크 추가 또는 제거 정보를 담은 DTO)
     * @return boolean (추가 후에는 true, 제거 후에는 false)
     */
    boolean addOrRemoveBookmark(BookmarkRequestDto requestDto);

    /**
     * 회원 아이디에 따른 모든 북마크 목록 페이지를 조회한다.
     *
     * @param userId (회원 아이디)
     * @param page   (페이지 번호)
     * @param size   (페이지 크기)
     * @return Page<BookmarkListResponseDto> (북마크 목록 정보를 담은 DTO 페이지)
     */
    @Transactional(readOnly = true)
    Page<BookmarkListResponseDto> findBookmarkPageByUserId(Long userId, int page, int size);

    /**
     * 회원 아이디에 따른 북마크 목록 중 옵션으로 필터링하여 조회한다.
     *
     * @param userId      (회원 아이디)
     * @param page        (페이지 번호)
     * @param size        (페이지 크기)
     * @param name        (레시피 이름)
     * @param time        (레시피 시간)
     * @param difficulty  (레시피 난이도)
     * @param composition (레시피 구성)
     * @return Page<BookmarkListResponseDto> (필터링된 북마크 목록 정보를 담은 DTO 페이지)
     */
    @Transactional(readOnly = true)
    Page<BookmarkListResponseDto> findBookmarkPageByOption(Long userId, int page, int size,
                                                           String name, String time, String difficulty, String composition);
}
