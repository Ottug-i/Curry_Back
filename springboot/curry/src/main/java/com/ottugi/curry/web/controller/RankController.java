package com.ottugi.curry.web.controller;

import com.ottugi.curry.service.rank.RankService;
import com.ottugi.curry.web.dto.rank.RankResponseDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags={"Rank API (인기 검색어 API)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class RankController {

    private final RankService rankService;

    @GetMapping("/api/rank/getRankList")
    public ResponseEntity<List<RankResponseDto>> getRankList(){
        return ResponseEntity.ok().body(rankService.getRankList());
    }
}
