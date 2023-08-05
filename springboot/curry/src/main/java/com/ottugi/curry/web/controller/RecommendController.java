package com.ottugi.curry.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ottugi.curry.service.recipe.RecipeService;
import com.ottugi.curry.service.recommend.RecommendService;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recommend.RatingRequestDto;
import com.ottugi.curry.web.dto.recommend.RatingResponseDto;
import com.ottugi.curry.web.dto.recommend.RecommendRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags={"Recommend API (레시피 추천 API)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class RecommendController {

    private final RecommendService recommendService;
    private final RecipeService recipeService;

    @GetMapping("/recommend/bookmark")
    @ApiOperation(value = "레시피 북마크 추천", notes = "북마크한 레시피와 비슷한 레시피를 추천하여 5개씩 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 기본키", example = "1", required = true),
            @ApiImplicitParam(name = "recipeId", value = "북마크한 레시피 아이디", example = "6842324", required = true),
            @ApiImplicitParam(name = "page", value = "페이지 번호", example = "1")
    })
    public ResponseEntity<List<RecipeListResponseDto>> getRecommendBookmark(@RequestParam Long userId, Long recipeId, int page) throws JsonProcessingException {

        List<Long> recipeIdList = recommendService.getRecommendBookmark(recipeId, page);
        return ResponseEntity.ok().body(recipeService.getRecommendList(new RecommendRequestDto(userId, recipeIdList)));
    }

    @GetMapping("/recommend/user")
    @ApiOperation(value = "레시피 평점 조회", notes = "유저 아이디에 따른 레시피 평점 정보를 리턴합니다. 조회되지 않을 경우 null을 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 기본키", example = "1", required = true),
            @ApiImplicitParam(name = "recipeId", value = "레시피 아이디", example = "6846342", required = true)
    })
    public ResponseEntity<RatingResponseDto> getUserRatings(@RequestParam Long userId, Long recipeId) throws JsonProcessingException {
        return ResponseEntity.ok().body(recommendService.getUserRating(userId, recipeId));
    }

    @PostMapping("/recommend/user")
    @ApiOperation(value = "레시피 평점 추가", notes = "유저 아이디에 따른 레시피 평점 정보를 추가/수정합니다.")
    public ResponseEntity<Boolean> updateUserRatings(@RequestBody RatingRequestDto ratingRequestDto) {
        return ResponseEntity.ok().body(recommendService.updateUserRating(ratingRequestDto));
    }

    @GetMapping("/recommend/rating")
    @ApiOperation(value = "레시피 평점 추천", notes = "레시피 평점순에 따라 10개씩 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 기본키", example = "1", required = true),
            @ApiImplicitParam(name = "page", value = "페이지 번호", example = "1"),
            @ApiImplicitParam(name = "bookmarkList", value = "북마크한 레시피 아이디", required = true)
    })
    public ResponseEntity<List<RecipeListResponseDto>> getRecommendBookmark(@RequestParam Long userId, int page, Long[] bookmarkList) throws JsonProcessingException {

        List<Long> recipeIdList = recommendService.getRecommendRating(userId, page, bookmarkList);
        return ResponseEntity.ok().body(recipeService.getRecommendList(new RecommendRequestDto(userId, recipeIdList)));
    }
}
