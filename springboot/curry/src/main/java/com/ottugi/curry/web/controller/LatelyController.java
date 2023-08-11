package com.ottugi.curry.web.controller;

import com.ottugi.curry.service.lately.LatelyService;
import com.ottugi.curry.web.dto.lately.LatelyListResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags={"Lately API (최근 본 레시피 API)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/lately")
public class LatelyController {

    private final LatelyService latelyService;

    @GetMapping("/getLatelyAll")
    @ApiOperation(value = "최근 본 레시피 리스트 조회", notes = "최근 본 레시피 리스트를 조회하여 리턴합니다.")
    @ApiImplicitParam(name = "userId", value = "회원 기본키", example = "1", required = true)
    public ResponseEntity<List<LatelyListResponseDto>> getLatelyAll(@RequestParam Long userId) {
        return ResponseEntity.ok().body(latelyService.getLatelyAll(userId));
    }
}
