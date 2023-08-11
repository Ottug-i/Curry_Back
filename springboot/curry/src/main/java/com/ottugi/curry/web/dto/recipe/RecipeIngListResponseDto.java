package com.ottugi.curry.web.dto.recipe;

import com.ottugi.curry.domain.recipe.Recipe;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@Getter
public class RecipeIngListResponseDto {

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

    public RecipeIngListResponseDto(List<String> ingredients, Recipe recipe, Boolean isBookmark) {
        this.recipeId = recipe.getRecipeId();
        this.name = recipe.getName();
        this.thumbnail = recipe.getThumbnail();
        this.time = recipe.getTime().getTimeName();
        this.difficulty = recipe.getDifficulty().getDifficulty();
        this.composition = recipe.getComposition().getComposition();
        this.ingredients = ingredientFilter(recipe.getIngredients(), ingredients);
        this.isBookmark = isBookmark;
    }

    // [] 섹션마다 재료 순서 필터링 후 합치기
    private String ingredientFilter(String ingredients, List<String> filterIngredients) {

        List<String> sections = extractSections(ingredients);

        StringBuilder resultBuilder = new StringBuilder();
        for (String section : sections) {
            resultBuilder.append(changeIngredientOrder(section, filterIngredients)).append(" ");
        }
        String result = resultBuilder.toString().trim();

        return result;
    }

    // [필수 재료] 재료1, 재료2, 재료3 [양념 재료] 재료1, 재료2, 지료3 에서 [ 기준으로 나누기
    private List<String> extractSections(String ingredients) {

        List<String> section = new ArrayList<>();

        String[] parts = ingredients.split("\\[");
        for (String part : parts) {
            if (!part.trim().isEmpty()) {
                section.add("[" + part.trim());
            }
        }

        return section;
    }

    // 재료 순서 바꾸기
    private String changeIngredientOrder(String ingredientSection, List<String> filterIngredients) {

        String[] ingredientSectionList = splitIngredients(ingredientSection);

        String[] parts = ingredientSectionList[1].split("\\|");
        List<String> allIngredients = new ArrayList<>(Arrays.asList(parts));
        List<String> modifiedIngredients = new ArrayList<>();

        for (String ingredient : filterIngredients) {
            Iterator<String> iterator = allIngredients.iterator();
            while (iterator.hasNext()) {
                String part = iterator.next();
                if (part.contains(ingredient)) {
                    modifiedIngredients.add(part);
                    iterator.remove();
                }
            }
        }

        modifiedIngredients.addAll(allIngredients);

        modifiedIngredients = removeSpacesFromIngredients(modifiedIngredients);

        return ingredientSectionList[0] + " " + String.join("| ", modifiedIngredients);
    }

    // [필수 재료]와 재료 나누기
    private String[] splitIngredients(String ingredientSection) {

        int bracketIndex = ingredientSection.indexOf("]");
        if (bracketIndex != -1) {
            String bracket = ingredientSection.substring(0, bracketIndex + 1).trim();
            String ingredients = ingredientSection.substring(bracketIndex + 1).trim();
            return new String[]{bracket, ingredients};
        } else {
            return new String[]{ingredientSection, ""};
        }
    }

    // 재료 리스트에서 공백 제거
    private List<String> removeSpacesFromIngredients(List<String> modifiedIngredients) {

        List<String> result = new ArrayList<>();
        for (String ingredient : modifiedIngredients) {
            result.add(ingredient.trim());
        }
        return result;
    }
}
