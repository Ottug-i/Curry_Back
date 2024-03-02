package com.ottugi.curry;

import static com.ottugi.curry.TestConstants.BOOKMARK_ID;
import static com.ottugi.curry.TestConstants.COMPOSITION;
import static com.ottugi.curry.TestConstants.DIFFICULTY;
import static com.ottugi.curry.TestConstants.EMAIL;
import static com.ottugi.curry.TestConstants.EXPIRED_TIME;
import static com.ottugi.curry.TestConstants.FAVORITE_GENRE;
import static com.ottugi.curry.TestConstants.GENRE;
import static com.ottugi.curry.TestConstants.ID;
import static com.ottugi.curry.TestConstants.INGREDIENT;
import static com.ottugi.curry.TestConstants.INGREDIENTS;
import static com.ottugi.curry.TestConstants.KEYWORD;
import static com.ottugi.curry.TestConstants.LATELY_ID;
import static com.ottugi.curry.TestConstants.NAME;
import static com.ottugi.curry.TestConstants.NEW_NICKNAME;
import static com.ottugi.curry.TestConstants.NICKNAME;
import static com.ottugi.curry.TestConstants.ORDERS;
import static com.ottugi.curry.TestConstants.PAGE;
import static com.ottugi.curry.TestConstants.PHOTO;
import static com.ottugi.curry.TestConstants.RATING;
import static com.ottugi.curry.TestConstants.RATING_ID;
import static com.ottugi.curry.TestConstants.RECIPE_ID;
import static com.ottugi.curry.TestConstants.ROLE;
import static com.ottugi.curry.TestConstants.SERVINGS;
import static com.ottugi.curry.TestConstants.SIZE;
import static com.ottugi.curry.TestConstants.THUMBNAIL;
import static com.ottugi.curry.TestConstants.TIME;
import static com.ottugi.curry.TestConstants.USER_ID;
import static com.ottugi.curry.TestConstants.VALUE;

import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.lately.Lately;
import com.ottugi.curry.domain.rank.Rank;
import com.ottugi.curry.domain.ratings.Ratings;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.token.Token;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.web.dto.auth.TokenResponseDto;
import com.ottugi.curry.web.dto.auth.UserSaveRequestDto;
import com.ottugi.curry.web.dto.bookmark.BookmarkRequestDto;
import com.ottugi.curry.web.dto.ratings.RatingRequestDto;
import com.ottugi.curry.web.dto.recommend.RecipeIngRequestDto;
import com.ottugi.curry.web.dto.recommend.RecommendRequestDto;
import com.ottugi.curry.web.dto.user.UserUpdateRequestDto;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TestObjectFactory {
    public static User initUser() {
        return User.builder()
                .id(USER_ID)
                .email(EMAIL)
                .nickName(NICKNAME)
                .favoriteGenre(FAVORITE_GENRE)
                .role(ROLE)
                .build();
    }

    public static UserSaveRequestDto initUserSaveRequestDto(User user) {
        return UserSaveRequestDto.builder()
                .email(user.getEmail())
                .nickName(user.getNickName())
                .build();
    }

    public static UserUpdateRequestDto initUserUpdateRequestDto(User user) {
        return UserUpdateRequestDto.builder()
                .id(user.getId())
                .nickName(NEW_NICKNAME)
                .build();
    }

    public static Token initToken() {
        return Token.builder()
                .key(EMAIL)
                .value(VALUE)
                .expiredTime(EXPIRED_TIME)
                .build();
    }

    public static TokenResponseDto initTokenResponseDto(User user) {
        return new TokenResponseDto(user, VALUE);
    }

    public static Recipe initRecipe() {
        return Recipe.builder()
                .id(ID)
                .recipeId(RECIPE_ID)
                .name(NAME)
                .thumbnail(THUMBNAIL)
                .time(TIME)
                .difficulty(DIFFICULTY)
                .composition(COMPOSITION)
                .ingredients(INGREDIENTS)
                .servings(SERVINGS)
                .orders(ORDERS)
                .photo(PHOTO)
                .genre(GENRE)
                .build();
    }

    public static Bookmark initBookmark() {
        return Bookmark.builder()
                .id(BOOKMARK_ID)
                .build();
    }

    public static BookmarkRequestDto initBookmarkRequestDto(Bookmark bookmark) {
        return BookmarkRequestDto.builder()
                .userId(bookmark.getUserId().getId())
                .recipeId(bookmark.getRecipeId().getRecipeId())
                .build();
    }

    public static Lately initLately() {
        return Lately.builder()
                .id(LATELY_ID)
                .build();
    }

    public static Rank initRank() {
        return Rank.builder()
                .name(KEYWORD)
                .build();
    }

    public static Ratings initRatings() {
        return Ratings.builder()
                .id(RATING_ID)
                .userId(USER_ID)
                .recipeId(RECIPE_ID)
                .rating(RATING)
                .build();
    }

    public static RatingRequestDto initRatingRequestDto(Ratings ratings) {
        Map<Long, Double> ratingMap = new HashMap<>();
        ratingMap.put(ratings.getRecipeId(), 3.0);
        return RatingRequestDto.builder()
                .userId(ratings.getUserId())
                .newUserRatingsDic(ratingMap)
                .build();
    }

    public static RecipeIngRequestDto initRecipeIngRequestDto(User user, Recipe recipe) {
        return RecipeIngRequestDto.builder()
                .userId(user.getId())
                .ingredients(Collections.singletonList(INGREDIENT))
                .time(recipe.getTime().getTimeName())
                .difficulty(recipe.getDifficulty().getDifficulty())
                .composition(recipe.getComposition().getComposition())
                .page(PAGE)
                .size(SIZE)
                .build();
    }

    public static RecommendRequestDto initRecommendRequestDto(User user, Recipe recipe) {
        return RecommendRequestDto.builder()
                .userId(user.getId())
                .recipeId(Collections.singletonList(recipe.getId()))
                .build();
    }
}
