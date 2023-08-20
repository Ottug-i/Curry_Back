package com.ottugi.curry.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.service.recommend.RecommendService;
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

    private List<String> ingredients = Arrays.asList("고구마", "올리고당");

    private List<Long> recipeIdList = new ArrayList<>();

    private Map<Long, Double> newUserRatingsDic = new HashMap<>();
    private List<Double> ratingInfo = Arrays.asList(6846342.0, 1.0, 4.0);

    private Long[] bookmarkList = {6842324L, 6845721L};

    private final Boolean isBookmark = true;

    private MockMvc mockMvc;

    @Mock
    private RecommendService recommendService;

    @InjectMocks
    private RecommendController recommendController;

    @BeforeEach
    public void setUp() {
        user = new User(USER_ID, EMAIL, NICKNAME, FAVORITE_GENRE, ROLE);
        recipe = new Recipe(ID, RECIPE_ID, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);
        mockMvc = MockMvcBuilders.standaloneSetup(recommendController).build();
    }

    @Test
    void 초기랜덤레시피평점() throws Exception {
        // given
        List<RecommendListResponseDto> recommendListResponseDtoList = new ArrayList<>();

        // when
        when(recommendService.getRandomRecipe()).thenReturn(recommendListResponseDtoList);

        // then
        mockMvc.perform(get("/api/recommend/initial"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(recommendListResponseDtoList.size())));
    }

    @Test
    void 레시피평점조회() throws Exception {
        // given
        RatingResponseDto ratingRequestDto = new RatingResponseDto(ratingInfo);

        // when
        when(recommendService.getUserRating(anyLong(), anyLong())).thenReturn(ratingRequestDto);

        // then
        mockMvc.perform(get("/api/recommend/rating")
                    .param("userId", String.valueOf(user.getId()))
                    .param("recipeId", String.valueOf(recipe.getRecipeId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.recipeId").value(6846342))
                .andExpect(jsonPath("$.rating").value(4.0));
    }

    @Test
    void 레시피평점추가또는수정() throws Exception {
        // given
        newUserRatingsDic.put(6847060L, 3.0);
        RatingRequestDto ratingRequestDto = new RatingRequestDto(user.getId(), newUserRatingsDic);

        // when
        when(recommendService.updateUserRating(any(RatingRequestDto.class))).thenReturn(true);

        // then
        mockMvc.perform(post("/api/recommend/rating")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ratingRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void 레시피평점삭제() throws Exception {
        // when
        when(recommendService.deleteUserRating(user.getId(), recipe.getRecipeId())).thenReturn(true);

        // then
        mockMvc.perform(delete("/api/recommend/rating")
                        .param("userId", String.valueOf(user.getId()))
                        .param("recipeId", String.valueOf(recipe.getRecipeId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void 재료추천레시피리스트조회() throws Exception {
        // given
        RecipeRequestDto recipeRequestDto = new RecipeRequestDto(user.getId(), Arrays.asList("고구마", "올리고당"), PAGE, SIZE);
        
        List<RecipeIngListResponseDto> recipeListResponseDtoList = new ArrayList<>();
        recipeListResponseDtoList.add(new RecipeIngListResponseDto(ingredients, recipe, isBookmark));

        Page<RecipeIngListResponseDto> pagedRecipeList = new PageImpl<>(recipeListResponseDtoList);

        // when
        when(recommendService.getIngredientsRecommendList(any(RecipeRequestDto.class))).thenReturn(pagedRecipeList);

        // then
        mockMvc.perform(post("/api/recommend/ingredients/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(recipeRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(pagedRecipeList.getSize())));
    }

    @Test
    void 북마크추천레시피리스트조회() throws Exception {
        // given
        List<RecipeListResponseDto> recipeListResponseDtoList = new ArrayList<>();

        // when
        when(recommendService.getRecommendBookmarkId(anyLong(), anyInt())).thenReturn(recipeIdList);
        when(recommendService.getBookmarkOrRatingRecommendList(any(RecommendRequestDto.class))).thenReturn(recipeListResponseDtoList);

        // then
        mockMvc.perform(get("/api/recommend/bookmark/list")
                        .param("userId", String.valueOf(user.getId()))
                        .param("recipeId", String.valueOf(recipe.getRecipeId()))
                        .param("page", String.valueOf(PAGE)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(recipeListResponseDtoList.size())));
    }

    @Test
    void 평점추천레시피리스트조회() throws Exception {
        // given
        List<RecipeListResponseDto> recipeListResponseDtoList = new ArrayList<>();

        // when
        when(recommendService.getRecommendRatingId(anyLong(), anyInt(), eq(bookmarkList))).thenReturn(recipeIdList);
        when(recommendService.getBookmarkOrRatingRecommendList(any(RecommendRequestDto.class))).thenReturn(recipeListResponseDtoList);

        // then
        mockMvc.perform(get("/api/recommend/bookmark/list")
                        .param("userId", String.valueOf(user.getId()))
                        .param("page", String.valueOf(PAGE))
                        .param("bookmarkList", "6842324", "6845721"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(recipeListResponseDtoList.size())));
    }
}