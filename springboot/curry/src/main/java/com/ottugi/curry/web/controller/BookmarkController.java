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

import java.util.List;

@Api(tags={"Bookmark API (북마크 레시피 API)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/api/bookmark/addAndRemoveBookmark")
    @ApiOperation(value = "북마크 레시피 추가/삭제", notes = "북마크 레시피를 추가한 후 true를 리턴합니다. 이미 북마크일 경우 북마크가 삭제되고 false를 리턴합니다.")
    public ResponseEntity<Boolean> addOrRemoveBookmark(@RequestBody BookmarkRequestDto bookmarkRequestDto) {
        return ResponseEntity.ok().body(bookmarkService.addOrRemoveBookmark(bookmarkRequestDto));
    }

    @GetMapping("/api/bookmark/getBookmarkAll")
    @ApiOperation(value = "북마크 레시피 리스트 조회", notes = "북마크 레시피 리스트를 조회하여 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 기본키", example = "1", required = true),
            @ApiImplicitParam(name = "page", value = "페이지 번호", example = "1"),
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", example = "10")
    })
    public ResponseEntity<Page<BookmarkListResponseDto>> getBookmarkAll(@RequestParam Long userId, int page, int size) {
        return ResponseEntity.ok().body(bookmarkService.getBookmarkAll(userId, page, size));
    }

    @GetMapping("/api/bookmark/searchByName")
    @ApiOperation(value = "북마크 레시피 리스트 중 이름으로 검색", notes = "북마크 레시피 리스트에서 이름으로 검색하여 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 기본키", example = "1", required = true),
            @ApiImplicitParam(name = "page", value = "페이지 번호", example = "1"),
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", example = "10"),
            @ApiImplicitParam(name = "name", value = "레시피 이름", example = "고구마", required = true)
    })
    public ResponseEntity<Page<BookmarkListResponseDto>> searchByName(@RequestParam Long userId, int page, int size, String name) {
        return ResponseEntity.ok().body(bookmarkService.searchByName(userId, page, size, name));
    }

    @GetMapping("/api/bookmark/searchByOption")
    @ApiOperation(value = "북마크 레시피 리스트 중 옵션으로 검색", notes = "북마크 레시피 리스트에서 옵션(시간/난이도/구성)으로 검색하여 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 기본키", example = "1", required = true),
            @ApiImplicitParam(name = "page", value = "페이지 번호", example = "1"),
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", example = "10"),
            @ApiImplicitParam(name = "time", value = "레시피 시간", example = "15분 이내"),
            @ApiImplicitParam(name = "difficulty", value = "레시피 난이도", example = "아무나"),
            @ApiImplicitParam(name = "composition", value = "레시피 구성", example = "든든하게")
    })
    public ResponseEntity<Page<BookmarkListResponseDto>> searchByOption(@RequestParam Long userId, int page, int size, String time, String difficulty, String composition) {
        return ResponseEntity.ok().body(bookmarkService.searchByOption(userId, page, size, time, difficulty, composition));
    }
}
