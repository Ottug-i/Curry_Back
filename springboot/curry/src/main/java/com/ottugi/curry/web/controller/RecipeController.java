package com.ottugi.curry.web.controller;

import com.ottugi.curry.service.recipe.RecipeService;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeRequestDto;
import com.ottugi.curry.web.dto.recipe.RecipeResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags={"Recipe API (레시피 API)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping("/api/recipe/getRecipeList")
    @ApiOperation(value = "재료에 따른 레시피 리스트 조회", notes = "재료에 따른 레시피 리스트를 조회하여 레시피 북마크 유무와 함께 리턴합니다.")
    public ResponseEntity<Page<RecipeListResponseDto>> getRecipeList(@RequestBody RecipeRequestDto recipeRequestDto) {
        return ResponseEntity.ok().body(recipeService.getRecipeList(recipeRequestDto));
    }

    @GetMapping("/api/recipe/getRecipeDetail")
    @ApiOperation(value = "레시피 상세 조회", notes = "레시피를 상세 조회하여 레시피 북마크 유무와 함께 리턴합니다. 이후 최근 본 레시피에 추가됩니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 기본키", example = "1", required = true),
            @ApiImplicitParam(name = "recipeId", value = "레시피 기본키", example = "6909678", required = true),
    })
    public ResponseEntity<RecipeResponseDto> getRecipeDetail(@RequestParam Long userId, Long recipeId) {
        return ResponseEntity.ok().body(recipeService.getRecipeDetail(userId, recipeId));
    }

    @GetMapping("/api/recipe/searchByBox")
    @ApiOperation(value = "레시피 텍스트 검색", notes = "레시피 리스트에서 텍스트 및 옵션(시간/난이도/구성)으로 검색하여 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 기본키", example = "1", required = true),
            @ApiImplicitParam(name = "name", value = "레시피 이름", example = "달걀빵"),
            @ApiImplicitParam(name = "time", value = "레시피 시간", example = "10분 이내"),
            @ApiImplicitParam(name = "difficulty", value = "레시피 난이도", example = "아무나"),
            @ApiImplicitParam(name = "composition", value = "레시피 구성", example = "가볍게")
    })
    public ResponseEntity<List<RecipeListResponseDto>> searchByBox(@RequestParam Long userId, String name, String time, String difficulty, String composition) {
        return ResponseEntity.ok().body(recipeService.searchByBox(userId, name, time, difficulty, composition));
    }
}
