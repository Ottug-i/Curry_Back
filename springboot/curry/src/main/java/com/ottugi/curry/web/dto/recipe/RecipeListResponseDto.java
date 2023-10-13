package com.ottugi.curry.web.dto.recipe;

import com.ottugi.curry.domain.recipe.Recipe;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class RecipeListResponseDto {

    @ApiModelProperty(notes = "레시피 아이디", example = "6842324")
    private Long recipeId;

    @ApiModelProperty(notes = "레시피 이름", example = "고구마맛탕")
    private String name;

    @ApiModelProperty(notes = "레시피 썸네일", example = "https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/828bccf4fdd0a71b6477a8e96e84906b1.png")
    private String thumbnail;

    @ApiModelProperty(notes = "레시피 시간", example = "60분 이내")
    private String time;

    @ApiModelProperty(notes = "레시피 난이도", example = "초급")
    private String difficulty;

    @ApiModelProperty(notes = "레시피 구성", example = "가볍게")
    private String composition;

    @ApiModelProperty(notes = "레시피 재료", example = " `[재료] 고구마| 식용유| 황설탕| 올리고당| 견과류| 물")
    private String ingredients;

    @ApiModelProperty(notes = "북마크 유무", example = "true")
    private Boolean isBookmark;

    @ApiModelProperty(notes = "재료 메인 장르", example = "vegetable")
    private String mainGenre;

    public RecipeListResponseDto(Recipe recipe, Boolean isBookmark) {
        this.recipeId = recipe.getRecipeId();
        this.name = recipe.getName();
        this.thumbnail = recipe.getThumbnail();
        this.time = recipe.getTime().getTimeName();
        this.difficulty = recipe.getDifficulty().getDifficulty();
        this.composition = recipe.getComposition().getComposition();
        this.ingredients = recipe.getIngredients();
        this.isBookmark = isBookmark;
        this.mainGenre = getArCharacterType(recipe);
    }

    // 추천 레시피에 따른 3D 모델 캐릭터 조회
    private String getArCharacterType(Recipe recipe) {
        String[] parts = recipe.getGenre().split("\\|");
        if (parts.length > 0) {
            String mainGenre = parts[0];
            String character = "";
            System.out.println(mainGenre);
            switch (mainGenre) {
                case "ing1":
                case "ing2":
                case "ing3":
                case "ing4":
                    character = "meat";
                    break;
                case "ing5":
                case "ing6":
                case "ing7":
                case "ing8":
                    character = "fish";
                    break;
                case "ing9":
                    character = "kimchi";
                    break;
                case "ing10":
                    character = "tofu";
                    break;
                case "ing11":
                    character = "egg";
                    break;
                case "ing12":
                    character = "mushroom";
                    break;
                case "ing13":
                case "ing14":
                case "ing15":
                case "ing16":
                case "ing17":
                case "ing18":
                    character = "vegetable";
                    break;
                case "ing19":
                case "ing20":
                case "ing21":
                case "ing22":
                case "ing23":
                case "ing24":
                    character = "fruit";
                    break;
                case "ing25":
                    character = "milk";
                    break;
            }
            return character;
        }
        else {
            return null;
        }
    }
}
