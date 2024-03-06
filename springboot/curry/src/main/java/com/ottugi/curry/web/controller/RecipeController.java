package com.ottugi.curry.web.controller;

import com.ottugi.curry.service.recipe.RecipeService;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Recipe API (레시피 API)"})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping
    @ApiOperation(value = "레시피 상세 조회", notes = "레시피를 상세 조회하여 레시피 북마크 유무와 함께 리턴합니다. 이후 최근 본 레시피에 추가됩니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 기본키", example = "1", required = true),
            @ApiImplicitParam(name = "recipeId", value = "레시피 아이디", example = "6909678", required = true)
    })
    public ResponseEntity<RecipeResponseDto> recipeDetails(@RequestParam @NotNull Long userId,
                                                           @RequestParam @NotNull Long recipeId) {
        return ResponseEntity.ok().body(recipeService.findRecipeByUserIdAndRecipeId(userId, recipeId));
    }

    @GetMapping("/search")
    @ApiOperation(value = "레시피 중 텍스트와 옵션으로 검색", notes = "레시피에서 텍스트 및 옵션(시간/난이도/구성)으로 검색하여 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 기본키", example = "1", required = true),
            @ApiImplicitParam(name = "page", value = "페이지 번호", example = "1"),
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", example = "10"),
            @ApiImplicitParam(name = "name", value = "검색어", example = "달걀"),
            @ApiImplicitParam(name = "time", value = "레시피 시간", example = "10분 이내"),
            @ApiImplicitParam(name = "difficulty", value = "레시피 난이도", example = "아무나"),
            @ApiImplicitParam(name = "composition", value = "레시피 구성", example = "가볍게")
    })
    public ResponseEntity<Page<RecipeListResponseDto>> recipeSearchOptionPage(@RequestParam @NotNull Long userId,
                                                                              @RequestParam @Min(1) int page,
                                                                              @RequestParam @Min(1) int size,
                                                                              @RequestParam(required = false) String name,
                                                                              @RequestParam(required = false) String time,
                                                                              @RequestParam(required = false) String difficulty,
                                                                              @RequestParam(required = false) String composition) {
        return ResponseEntity.ok().body(recipeService.findRecipePageBySearchBox(userId, page, size, name, time, difficulty, composition));
    }
}
