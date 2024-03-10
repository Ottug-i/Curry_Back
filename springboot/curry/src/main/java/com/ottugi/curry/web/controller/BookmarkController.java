package com.ottugi.curry.web.controller;

import com.ottugi.curry.service.bookmark.BookmarkService;
import com.ottugi.curry.web.dto.bookmark.BookmarkListResponseDto;
import com.ottugi.curry.web.dto.bookmark.BookmarkRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Bookmark API (북마크 API)"})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/bookmark")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping
    @ApiOperation(value = "북마크 레시피 추가/삭제", notes = "북마크 레시피를 추가한 후 true를 리턴합니다. 이미 북마크일 경우 북마크가 삭제되고 false를 리턴합니다.")
    public ResponseEntity<Boolean> bookmarkSave(@RequestBody @Valid BookmarkRequestDto requestDto) {
        return ResponseEntity.ok().body(bookmarkService.addOrRemoveBookmark(requestDto));
    }

    @GetMapping("/list")
    @ApiOperation(value = "북마크 레시피 조회", notes = "북마크 레시피를 조회하여 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 기본키", example = "1", required = true),
            @ApiImplicitParam(name = "page", value = "페이지 번호", example = "1"),
            @ApiImplicitParam(name = "size", value = "페이지 크기", example = "10")
    })
    public ResponseEntity<Page<BookmarkListResponseDto>> bookmarkPage(@RequestParam @NotNull Long userId,
                                                                      @RequestParam @Min(1) int page,
                                                                      @RequestParam @Min(1) int size) {
        return ResponseEntity.ok().body(bookmarkService.findBookmarkPageByUserId(userId, page, size));
    }

    @GetMapping("/search")
    @ApiOperation(value = "북마크 레시피 중 이름과 옵션으로 검색", notes = "북마크 레시피에서 이름과 옵션(시간/난이도/구성)으로 검색하여 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 기본키", example = "1", required = true),
            @ApiImplicitParam(name = "page", value = "페이지 번호", example = "1"),
            @ApiImplicitParam(name = "size", value = "페이지 크기", example = "10"),
            @ApiImplicitParam(name = "name", value = "레시피 이름", example = "고구마"),
            @ApiImplicitParam(name = "time", value = "레시피 시간", example = "15분 이내"),
            @ApiImplicitParam(name = "difficulty", value = "레시피 난이도", example = "아무나"),
            @ApiImplicitParam(name = "composition", value = "레시피 구성", example = "든든하게")
    })
    public ResponseEntity<Page<BookmarkListResponseDto>> bookmarkSearchOptionPage(@RequestParam @NotNull Long userId,
                                                                                  @RequestParam @Min(1) int page,
                                                                                  @RequestParam @Min(1) int size,
                                                                                  @RequestParam(required = false) String name,
                                                                                  @RequestParam(required = false) String time,
                                                                                  @RequestParam(required = false) String difficulty,
                                                                                  @RequestParam(required = false) String composition) {
        return ResponseEntity.ok().body(bookmarkService.findBookmarkPageByOption(userId, page, size, name, time, difficulty, composition));
    }
}
