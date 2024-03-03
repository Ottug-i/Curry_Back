package com.ottugi.curry.web.controller;

import static com.ottugi.curry.TestConstants.PAGE;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ottugi.curry.TestObjectFactory;
import com.ottugi.curry.config.SecurityConfig;
import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.ratings.Ratings;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.jwt.JwtAuthenticationFilter;
import com.ottugi.curry.service.ratings.RatingsService;
import com.ottugi.curry.service.recommend.RecommendService;
import com.ottugi.curry.web.dto.ratings.RatingRequestDto;
import com.ottugi.curry.web.dto.ratings.RatingResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recommend.RecipeIngListResponseDto;
import com.ottugi.curry.web.dto.recommend.RecipeIngRequestDto;
import com.ottugi.curry.web.dto.recommend.RecommendListResponseDto;
import com.ottugi.curry.web.dto.recommend.RecommendRequestDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = RecommendController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)})
@WithMockUser
class RecommendControllerTest {
    private User user;
    private Recipe recipe;
    private RatingRequestDto ratingRequestDto;
    private RatingResponseDto ratingResponseDto;
    private RecipeIngRequestDto recipeIngRequestDto;
    private List<RecipeListResponseDto> recipeListResponseDtoList;
    private Page<RecipeIngListResponseDto> recipeIngListResponseDtoPage;
    private List<RecommendListResponseDto> recommendListResponseDtoList;
    private Long[] bookmarkList;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecommendService recommendService;

    @MockBean
    private RatingsService ratingsService;

    @BeforeEach
    public void setUp() {
        user = TestObjectFactory.initUser();
        recipe = TestObjectFactory.initRecipe();
        Bookmark bookmark = TestObjectFactory.initBookmark();
        bookmark.setUser(user);
        bookmark.setRecipe(recipe);
        Ratings ratings = TestObjectFactory.initRatings();

        ratingRequestDto = TestObjectFactory.initRatingRequestDto(ratings);
        ratingResponseDto = TestObjectFactory.initRatingResponseDto(ratings);
        recipeIngRequestDto = TestObjectFactory.initRecipeIngRequestDto(user, recipe);
        recipeListResponseDtoList = TestObjectFactory.initRecipeListResponseDtoList(recipe);
        recipeIngListResponseDtoPage = TestObjectFactory.initRecipeIngListResponseDtoPage(recipe);
        recommendListResponseDtoList = TestObjectFactory.initRecommendListResponseDtoList(recipe);

        bookmarkList = new Long[]{bookmark.getRecipeId().getRecipeId()};
    }

    @Test
    @DisplayName("초기 평점을 위한 랜덤 레시피 목록 조회 테스트")
    void testRandomRecipeList() throws Exception {
        when(ratingsService.findRandomRecipeListForResearch()).thenReturn(recommendListResponseDtoList);

        mockMvc.perform(get("/api/recommend/initial")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(recommendListResponseDtoList.size())));
    }

    @Test
    @DisplayName("레시피 평점 조회 테스트")
    void testUserRatingDetails() throws Exception {
        when(ratingsService.findUserRating(anyLong(), anyLong())).thenReturn(ratingResponseDto);

        mockMvc.perform(get("/api/recommend/rating")
                        .param("userId", String.valueOf(user.getId()))
                        .param("recipeId", String.valueOf(recipe.getRecipeId()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(ratingResponseDto.getUserId()))
                .andExpect(jsonPath("$.recipeId").value(ratingResponseDto.getRecipeId()))
                .andExpect(jsonPath("$.rating").value(ratingResponseDto.getRating()));
    }

    @Test
    @DisplayName("레시피 평점 추가 또는 업데이트 테스트")
    void testUserRatingSave() throws Exception {
        when(ratingsService.addOrUpdateUserRating(any(RatingRequestDto.class))).thenReturn(true);

        mockMvc.perform(post("/api/recommend/rating")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ratingRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    @DisplayName("레시피 평점 삭제 테스트")
    void testUserRatingRemove() throws Exception {
        when(ratingsService.removeUserRating(anyLong(), anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/recommend/rating")
                        .param("userId", String.valueOf(user.getId()))
                        .param("recipeId", String.valueOf(recipe.getRecipeId()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    @DisplayName("재료 인식에 따른 추천 레시피 조회 테스트")
    void testRecipePageByIngredientsDetection() throws Exception {
        when(recommendService.findRecipePageByIngredientsDetection(any(RecipeIngRequestDto.class))).thenReturn(recipeIngListResponseDtoPage);

        mockMvc.perform(post("/api/recommend/ingredients/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(recipeIngRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(recipeIngListResponseDtoPage.getSize())));
    }

    @Test
    @DisplayName("북마크에 따른 추천 레시피 조회 테스트")
    void testRecipeListByBookmarkRecommend() throws Exception {
        when(recommendService.findBookmarkOrRatingRecommendList(any(RecommendRequestDto.class))).thenReturn(recipeListResponseDtoList);

        mockMvc.perform(get("/api/recommend/bookmark/list")
                        .param("userId", String.valueOf(user.getId()))
                        .param("recipeId", String.valueOf(recipe.getRecipeId()))
                        .param("page", String.valueOf(PAGE))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(recipeListResponseDtoList.size())));
    }

    @Test
    @DisplayName("레시피 평점에 따른 추천 레시피 조회 테스트")
    void testRecipeListByRatingRecommend() throws Exception {
        when(recommendService.findBookmarkOrRatingRecommendList(any(RecommendRequestDto.class))).thenReturn(recipeListResponseDtoList);

        mockMvc.perform(get("/api/recommend/bookmark/list")
                        .param("userId", String.valueOf(user.getId()))
                        .param("page", String.valueOf(PAGE))
                        .param("bookmarkList", String.valueOf(bookmarkList[0]))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(recipeListResponseDtoList.size())));
    }
}