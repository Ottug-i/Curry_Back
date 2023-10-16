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

    @GetMapping("/list")
    @ApiOperation(value = "최근 본 레시피 조회", notes = "최근 본 레시피를 조회하여 리턴합니다.")
    @ApiImplicitParam(name = "userId", value = "회원 기본키", example = "1", required = true)
    public ResponseEntity<List<LatelyListResponseDto>> getLatelyAll(@RequestParam Long userId) {
        return ResponseEntity.ok().body(latelyService.getLatelyAll(userId));
    }

    @GetMapping("/character")
    @ApiOperation(value = "최근 본 레시피에 따른 3D 모델 캐릭터 조회", notes = "최근 본 레시피의 메인 재료에 따른 3D 모델 캐릭터를 조회합니다.")
    @ApiImplicitParam(name = "userId", value = "회원 기본키", example = "1", readOnly = true)
    public ResponseEntity<String> getLatelyCharacter(@RequestParam Long userId) {
        return ResponseEntity.ok().body(latelyService.getLatelyCharacter(userId));
    }
}
