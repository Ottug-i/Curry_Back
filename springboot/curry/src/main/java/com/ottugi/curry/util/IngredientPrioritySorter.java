package com.ottugi.curry.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class IngredientPrioritySorter {
    // [] 각 섹션별 재료 필터링 및 순서 변경 후 결합
    public static String prioritizeIngredients(String ingredients, List<String> priorityIngredients) {
        List<String> sections = extractSections(ingredients);
        StringBuilder resultBuilder = new StringBuilder();
        for (String section : sections) {
            resultBuilder.append(prioritizeIngredientOrder(section, priorityIngredients)).append(" ");
        }
        return resultBuilder.toString().trim();
    }

    // [필수 재료] 재료1, 재료2, 재료3 [양념 재료] 재료1, 재료2, 지료3 에서 [ 기준으로 재료 섹션 추출
    private static List<String> extractSections(String ingredients) {
        List<String> section = new ArrayList<>();
        String[] parts = ingredients.split("\\[");
        for (String part : parts) {
            if (!part.trim().isEmpty()) {
                section.add("[" + part.trim());
            }
        }
        return section;
    }

    // 재료 우선순위에 따라 순서 변경
    private static String prioritizeIngredientOrder(String ingredientSection, List<String> priorityIngredients) {
        String[] sectionParts = splitIngredients(ingredientSection);
        List<String> allIngredients = Arrays.asList(sectionParts[1].split("\\|"));
        List<String> modifiedIngredients = new ArrayList<>();
        for (String priorityIngredient : priorityIngredients) {
            Iterator<String> iterator = allIngredients.iterator();
            while (iterator.hasNext()) {
                String ingredient = iterator.next();
                if (ingredient.contains(priorityIngredient)) {
                    modifiedIngredients.add(ingredient);
                    iterator.remove();
                }
            }
        }
        modifiedIngredients.addAll(allIngredients);
        modifiedIngredients = removeSpacesFromIngredients(modifiedIngredients);
        return sectionParts[0] + " " + String.join("| ", modifiedIngredients);
    }

    // 재료 섹션을 [필수 재료]와 재료로 분리
    private static String[] splitIngredients(String ingredientSection) {
        int bracketIndex = ingredientSection.indexOf("]");
        if (bracketIndex != -1) {
            String bracket = ingredientSection.substring(0, bracketIndex + 1).trim();
            String ingredients = ingredientSection.substring(bracketIndex + 1).trim();
            return new String[]{bracket, ingredients};
        }
        return new String[]{ingredientSection, ""};
    }

    // 재료 리스트에서 공백 제거
    private static List<String> removeSpacesFromIngredients(List<String> modifiedIngredients) {
        List<String> result = new ArrayList<>();
        for (String ingredient : modifiedIngredients) {
            result.add(ingredient.trim());
        }
        return result;
    }
}
