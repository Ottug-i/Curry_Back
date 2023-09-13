package com.ottugi.curry.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.ratings.Ratings;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.service.ratings.RatingsService;
import com.ottugi.curry.service.recommend.RecommendService;
import com.ottugi.curry.web.dto.ratings.RatingRequestDto;
import com.ottugi.curry.web.dto.ratings.RatingResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeRequestDto;
import com.ottugi.curry.web.dto.recommend.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static com.ottugi.curry.TestConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RecommendControllerTest {

    private User user;
    private Recipe recipe;
    private Bookmark bookmark;
    private Ratings ratings;

    private final Boolean isBookmark = true;
    private final String ingredient1 = "고구마";
    private final String ingredient2 = "올리고당";
    private List<String> ingredients = new ArrayList<>();
    private List<Long> recipeIdList = new ArrayList<>();
    private Long[] bookmarkList;
    private Map<Long, Double> newUserRatingsDic = new HashMap<>();

    private MockMvc mockMvc;

    @Mock
    private RecommendService recommendService;

    @Mock
    private RatingsService ratingsService;

    @InjectMocks
    private RecommendController recommendController;

    @BeforeEach
    public void setUp() {
        user = new User(USER_ID, EMAIL, NICKNAME, FAVORITE_GENRE, ROLE);
        recipe = new Recipe(ID, RECIPE_ID, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);
        bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setRecipe(recipe);
        ratings = new Ratings(RATING_ID, USER_ID, RATING_ID, RATING);

        ingredients.add(ingredient1);
        ingredients.add(ingredient2);
        recipeIdList.add(recipe.getRecipeId());
        bookmarkList = new Long[]{bookmark.getRecipeId().getRecipeId()};
        newUserRatingsDic.put(recipe.getRecipeId(), 3.0);

        mockMvc = MockMvcBuilders.standaloneSetup(recommendController).build();
    }

    @Test
    void 초기_평점을_위한_랜덤_레시피_목록_조회() throws Exception {
        // given
        List<RecommendListResponseDto> recommendListResponseDtoList = new ArrayList<>();
        recommendListResponseDtoList.add(new RecommendListResponseDto(recipe));
        when(ratingsService.getRandomRecipe()).thenReturn(recommendListResponseDtoList);

        // when, then
        mockMvc.perform(get("/api/recommend/initial"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(recommendListResponseDtoList.size())));
    }

    @Test
    void 레시피_평점_조회() throws Exception {
        // given
        RatingResponseDto ratingRequestDto = new RatingResponseDto(ratings);
        when(ratingsService.getUserRating(anyLong(), anyLong())).thenReturn(ratingRequestDto);

        // when, then
        mockMvc.perform(get("/api/recommend/rating")
                    .param("userId", String.valueOf(user.getId()))
                    .param("recipeId", String.valueOf(recipe.getRecipeId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(user.getId()))
                .andExpect(jsonPath("$.recipeId").value(recipe.getRecipeId()))
                .andExpect(jsonPath("$.rating").value(RATING));
    }

    @Test
    void 레시피_평점_조회_및_업데이트() throws Exception {
        // given
        when(ratingsService.updateUserRating(any(RatingRequestDto.class))).thenReturn(true);

        // when, then
        RatingRequestDto ratingRequestDto = new RatingRequestDto(user.getId(), newUserRatingsDic);
        mockMvc.perform(post("/api/recommend/rating")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ratingRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void 레시피_평점_삭제() throws Exception {
        // given
        when(ratingsService.deleteUserRating(anyLong(), anyLong())).thenReturn(true);

        // when, then
        mockMvc.perform(delete("/api/recommend/rating")
                        .param("userId", String.valueOf(user.getId()))
                        .param("recipeId", String.valueOf(recipe.getRecipeId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void 재료로_레시피_목록_조회() throws Exception {
        // given
        List<RecipeIngListResponseDto> recipeListResponseDtoList = new ArrayList<>();
        recipeListResponseDtoList.add(new RecipeIngListResponseDto(ingredients, recipe, isBookmark));
        Page<RecipeIngListResponseDto> pagedRecipeList = new PageImpl<>(recipeListResponseDtoList);
        when(recommendService.getIngredientsRecommendList(any(RecipeRequestDto.class))).thenReturn(pagedRecipeList);

        // when, then
        RecipeRequestDto recipeRequestDto = new RecipeRequestDto(user.getId(), ingredients, TIME.getTimeName(), DIFFICULTY.getDifficulty(), COMPOSITION.getComposition(), PAGE, SIZE);
        mockMvc.perform(post("/api/recommend/ingredients/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(recipeRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(pagedRecipeList.getSize())));
    }

    @Test
    void 북마크_추천_레시피_목록_조회() throws Exception {
        // given
        List<RecipeListResponseDto> recipeListResponseDtoList = new ArrayList<>();
        recipeListResponseDtoList.add(new RecipeListResponseDto(recipe, isBookmark));
        when(recommendService.getRecommendBookmarkId(anyLong(), anyInt())).thenReturn(recipeIdList);
        when(recommendService.getBookmarkOrRatingRecommendList(any(RecommendRequestDto.class))).thenReturn(recipeListResponseDtoList);

        // when, then
        mockMvc.perform(get("/api/recommend/bookmark/list")
                        .param("userId", String.valueOf(user.getId()))
                        .param("recipeId", String.valueOf(recipe.getRecipeId()))
                        .param("page", String.valueOf(PAGE)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(recipeListResponseDtoList.size())));
    }

    @Test
    void 평점_추천_레시피_목록_조회() throws Exception {
        // given
        List<RecipeListResponseDto> recipeListResponseDtoList = new ArrayList<>();
        recipeListResponseDtoList.add(new RecipeListResponseDto(recipe, isBookmark));
        when(recommendService.getRecommendRatingId(anyLong(), anyInt(), any())).thenReturn(recipeIdList);
        when(recommendService.getBookmarkOrRatingRecommendList(any(RecommendRequestDto.class))).thenReturn(recipeListResponseDtoList);

        // when, then
        mockMvc.perform(get("/api/recommend/bookmark/list")
                        .param("userId", String.valueOf(user.getId()))
                        .param("page", String.valueOf(PAGE))
                        .param("bookmarkList", String.valueOf(bookmarkList[0])))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(recipeListResponseDtoList.size())));
    }
}