package com.ottugi.curry.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ottugi.curry.service.ratings.RatingsService;
import com.ottugi.curry.service.recommend.RecommendService;
import com.ottugi.curry.web.dto.ratings.RatingRequestDto;
import com.ottugi.curry.web.dto.ratings.RatingResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeRequestDto;
import com.ottugi.curry.web.dto.recommend.RecipeIngListResponseDto;
import com.ottugi.curry.web.dto.recommend.RecommendListResponseDto;
import com.ottugi.curry.web.dto.recommend.RecommendRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Recommend API (레시피 추천 API)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/recommend")
public class RecommendController {
    private final RatingsService ratingsService;
    private final RecommendService recommendService;

    @GetMapping("/initial")
    @ApiOperation(value = "초기 랜덤 레시피 평점", notes = "초기 레시피 선호도 조사를 위한 10개의 랜덤 레시피를 리턴합니다.")
    public ResponseEntity<List<RecommendListResponseDto>> randomRecipeList() {
        return ResponseEntity.ok().body(ratingsService.findRandomRecipeListForResearch());
    }

    @GetMapping("/rating")
    @ApiOperation(value = "레시피 평점 조회", notes = "유저 아이디에 따른 레시피 평점 정보를 리턴합니다. 조회되지 않을 경우 null을 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 기본키", example = "1", required = true),
            @ApiImplicitParam(name = "recipeId", value = "레시피 아이디", example = "6846342", required = true)
    })
    public ResponseEntity<RatingResponseDto> userRatingDetails(@RequestParam Long userId, Long recipeId) throws JsonProcessingException {
        return ResponseEntity.ok().body(ratingsService.findUserRating(userId, recipeId));
    }

    @PostMapping("/rating")
    @ApiOperation(value = "레시피 평점 추가/수정", notes = "유저 아이디에 따른 레시피 평점 정보를 추가/수정합니다.")
    public ResponseEntity<Boolean> userRatingSave(@RequestBody RatingRequestDto ratingRequestDto) {
        return ResponseEntity.ok().body(ratingsService.addOrUpdateUserRating(ratingRequestDto));
    }

    @DeleteMapping("/rating")
    @ApiOperation(value = "레시피 평점 삭제", notes = "유저 아이디에 따른 레시피 평점 정보를 삭제합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 기본키", example = "1", required = true),
            @ApiImplicitParam(name = "recipeId", value = "레시피 아이디", example = "6846342", required = true)
    })
    public ResponseEntity<Boolean> userRatingRemove(@RequestParam Long userId, Long recipeId) {
        return ResponseEntity.ok().body(ratingsService.deleteUserRating(userId, recipeId));
    }

    @PostMapping("/ingredients/list")
    @ApiOperation(value = "재료 인식에 따른 추천 레시피 조회", notes = "재료 인식에 따른 레시피를 조회하여 레시피 북마크 유무와 함께 리턴합니다.")
    public ResponseEntity<Page<RecipeIngListResponseDto>> recipePageByIngredientsDetection(@RequestBody RecipeRequestDto recipeRequestDto) {
        return ResponseEntity.ok().body(recommendService.findRecipePageByIngredientsDetection(recipeRequestDto));
    }

    @GetMapping("/bookmark/list")
    @ApiOperation(value = "북마크에 따른 추천 레시피 조회", notes = "북마크한 레시피와 비슷한 레시피를 추천하여 5개씩 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 기본키", example = "1", required = true),
            @ApiImplicitParam(name = "recipeId", value = "북마크한 레시피 아이디", example = "6842324", required = true),
            @ApiImplicitParam(name = "page", value = "페이지 번호", example = "1")
    })
    public ResponseEntity<List<RecipeListResponseDto>> recipeListByBookmarkRecommend(@RequestParam Long userId, Long recipeId, int page)
            throws JsonProcessingException {
        List<Long> recipeIdList = recommendService.findRecipeIdListByBookmarkRecommend(recipeId, page);
        return ResponseEntity.ok().body(recommendService.findBookmarkOrRatingRecommendList(new RecommendRequestDto(userId, recipeIdList)));
    }

    @GetMapping("/rating/list")
    @ApiOperation(value = "레시피 평점에 따른 추천 레시피 조회", notes = "레시피 평점순에 따라 레시피를 추천하여 10개씩 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 기본키", example = "1", required = true),
            @ApiImplicitParam(name = "page", value = "페이지 번호", example = "1"),
            @ApiImplicitParam(name = "bookmarkList", value = "북마크한 레시피 아이디", required = true)
    })
    public ResponseEntity<List<RecipeListResponseDto>> recipeListByRatingRecommend(@RequestParam Long userId, int page, Long[] bookmarkList)
            throws JsonProcessingException {
        List<Long> recipeIdList = recommendService.findRecipeIdListByRatingRecommend(userId, page, bookmarkList);
        return ResponseEntity.ok().body(recommendService.findBookmarkOrRatingRecommendList(new RecommendRequestDto(userId, recipeIdList)));
    }
}
