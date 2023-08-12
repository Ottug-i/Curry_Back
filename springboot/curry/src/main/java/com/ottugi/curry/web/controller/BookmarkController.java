package com.ottugi.curry.web.controller;

import com.ottugi.curry.service.bookmark.BookmarkService;
import com.ottugi.curry.web.dto.bookmark.BookmarkListResponseDto;
import com.ottugi.curry.web.dto.bookmark.BookmarkRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags={"Bookmark API (북마크 API)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping
    @ApiOperation(value = "북마크 레시피 추가/삭제", notes = "북마크 레시피를 추가한 후 true를 리턴합니다. 이미 북마크일 경우 북마크가 삭제되고 false를 리턴합니다.")
    public ResponseEntity<Boolean> addOrRemoveBookmark(@RequestBody BookmarkRequestDto bookmarkRequestDto) {
        return ResponseEntity.ok().body(bookmarkService.addOrRemoveBookmark(bookmarkRequestDto));
    }

    @GetMapping("/list")
    @ApiOperation(value = "북마크 레시피 조회", notes = "북마크 레시피를 조회하여 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 기본키", example = "1", required = true),
            @ApiImplicitParam(name = "page", value = "페이지 번호", example = "1"),
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", example = "10")
    })
    public ResponseEntity<Page<BookmarkListResponseDto>> getBookmarkAll(@RequestParam Long userId, int page, int size) {
        return ResponseEntity.ok().body(bookmarkService.getBookmarkAll(userId, page, size));
    }

    @GetMapping("/search")
    @ApiOperation(value = "북마크 레시피 중 이름과 옵션으로 검색", notes = "북마크 레시피에서 이름과 옵션(시간/난이도/구성)으로 검색하여 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 기본키", example = "1", required = true),
            @ApiImplicitParam(name = "page", value = "페이지 번호", example = "1"),
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", example = "10"),
            @ApiImplicitParam(name = "name", value = "레시피 이름", example = "고구마"),
            @ApiImplicitParam(name = "time", value = "레시피 시간", example = "15분 이내"),
            @ApiImplicitParam(name = "difficulty", value = "레시피 난이도", example = "아무나"),
            @ApiImplicitParam(name = "composition", value = "레시피 구성", example = "든든하게")
    })
    public ResponseEntity<Page<BookmarkListResponseDto>> searchBookmark(@RequestParam Long userId, int page, int size, String name, String time, String difficulty, String composition) {
        return ResponseEntity.ok().body(bookmarkService.searchBookmark(userId, page, size, name, time, difficulty, composition));
    }
}
