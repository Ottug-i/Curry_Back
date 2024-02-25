package com.ottugi.curry.web.controller;

import com.ottugi.curry.service.rank.RankService;
import com.ottugi.curry.web.dto.rank.RankResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Rank API (인기 검색어 API)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/rank")
public class RankController {
    private final RankService rankService;

    @GetMapping("/list")
    @ApiOperation(value = "인기 검색어 조회", notes = "인기 검색어 10개를 조회하여 리턴합니다.")
    public ResponseEntity<List<RankResponseDto>> rankList() {
        return ResponseEntity.ok().body(rankService.findTopRankList());
    }
}
