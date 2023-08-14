package com.ottugi.curry;

import com.ottugi.curry.domain.recipe.Composition;
import com.ottugi.curry.domain.recipe.Difficulty;
import com.ottugi.curry.domain.recipe.Servings;
import com.ottugi.curry.domain.recipe.Time;
import com.ottugi.curry.domain.user.Role;
import org.springframework.boot.test.context.TestConfiguration;

import java.time.Duration;

@TestConfiguration
public class TestConstants {

    // User
    public static final Long USER_ID = 1L;
    public static final String EMAIL = "wn8925@gmail.com";
    public static final String NICKNAME = "가경";
    public static final String NEW_NICKNAME = "가경이";
    public static final String FAVORITE_GENRE = "ing15";
    public static final String NEW_FAVORITE_GENRE = "ing16";
    public static final Role ROLE = Role.ofRole("일반 사용자");
    public static final Role NEW_ROLE = Role.ofRole("관리자");

    // Token
    public static final String VALUE = "token";
    public static final Long EXPIRED_TIME = Duration.ofDays(14).toMillis();;

    // Recipe
    public static final Long RECIPE_ID = 6842324L;
    public static final String NAME = "고구마맛탕";
    public static final String THUMBNAIL = "https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/828bccf4fdd0a71b6477a8e96e84906b1.png";
    public static final Time TIME = Time.ofTime("60분 이내");
    public static final Difficulty DIFFICULTY = Difficulty.ofDifficulty("초급");
    public static final Composition COMPOSITION = Composition.ofComposition("가볍게");
    public static final String INGREDIENTS = "[재료] 고구마| 식용유| 황설탕| 올리고당| 견과류| 물";
    public static final Servings SERVINGS = Servings.ofServings("2인분");
    public static final String ORDERS = "|1. 바삭하게 튀기는 꿀팁|2. 달콤한 소스 꿀팁|3. 더 건강하게 먹는 꿀팁";
    public static final String PHOTO = "|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/4c9918cf77a109d28b389e6bc753b4bd1.jpg|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/66e8c5f5932e195e7b5405d110a6e67e1.jpg|https://recipe1.ezmember.co.kr/cache/recipe/2016/01/29/8628264d141fa54487461d41a45d905f1.jpg";
    public static final String GENRE = "ing13|ing21";

    // Rank
    public static final String KEYWORD = "망고";
    public static final int SCORE = 1;
}