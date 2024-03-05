package com.ottugi.curry.service.recommend;

import static com.ottugi.curry.domain.bookmark.BookmarkTest.BOOKMARK_ID;
import static com.ottugi.curry.domain.recipe.RecipeTest.PAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ottugi.curry.config.GlobalConfig;
import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.bookmark.BookmarkTest;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeTest;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserTest;
import com.ottugi.curry.service.recipe.RecipeService;
import com.ottugi.curry.service.user.UserService;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recommend.RecipeIngListResponseDto;
import com.ottugi.curry.web.dto.recommend.RecipeIngRequestDto;
import com.ottugi.curry.web.dto.recommend.RecipeIngRequestDtoTest;
import com.ottugi.curry.web.dto.recommend.RecommendRequestDto;
import com.ottugi.curry.web.dto.recommend.RecommendRequestDtoTest;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class RecommendServiceTest {
    private User user;
    private Recipe recipe;

    @Mock
    private UserService userService;
    @Mock
    private RecipeService recipeService;
    @Mock
    private GlobalConfig config;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private RecommendServiceImpl recommendService;

    @BeforeEach
    public void setUp() {
        user = UserTest.initUser();
        recipe = RecipeTest.initRecipe();
        Bookmark bookmark = BookmarkTest.initBookmark();
        bookmark.setUser(user);
        bookmark.setRecipe(recipe);
    }

    @Test
    @DisplayName("객체 인식으로 검출된 재료를 포함하는 레시피 목록 조회 테스트")
    void testFindRecipePageByIngredientsDetection() {
        when(userService.findUserByUserId(anyLong())).thenReturn(user);
        when(recipeService.findByRecipeListByIngredientsContaining(anyString())).thenReturn(Collections.singletonList(recipe));
        when(recipeService.filterPredicateForOptions(anyString(), anyString(), anyString())).thenReturn(recipe -> true);
        when(recipeService.isRecipeBookmarked(any(User.class), any(Recipe.class))).thenReturn(true);

        RecipeIngRequestDto recipeIngRequestDto = RecipeIngRequestDtoTest.initRecipeIngRequestDto(user, recipe);
        Page<RecipeIngListResponseDto> result = recommendService.findRecipePageByIngredientsDetection(recipeIngRequestDto);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());

        verify(userService, times(1)).findUserByUserId(anyLong());
        verify(recipeService, times(1)).findByRecipeListByIngredientsContaining(anyString());
        verify(recipeService, times(1)).filterPredicateForOptions(anyString(), anyString(), anyString());
        verify(recipeService, times(1)).isRecipeBookmarked(any(User.class), any(Recipe.class));
    }

    @Test
    @DisplayName("북마크 추천 레시피 아이디 목록 조회 테스트")
    void testFindRecipeIdListByBookmarkRecommend() throws JsonProcessingException {
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn("[1]");

        List<Long> result = recommendService.findRecipeIdListByBookmarkRecommend(recipe.getRecipeId(), PAGE);

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(restTemplate, times(1)).getForObject(anyString(), eq(String.class));
    }

    @Test
    @DisplayName("북마크 추천 레시피 아이디 목록 조회 시 빈 리스트 반환 테스트")
    void testFindRecipeIdListByBookmarkRecommendWithEmpty() throws JsonProcessingException {
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(null);

        List<Long> result = recommendService.findRecipeIdListByBookmarkRecommend(recipe.getRecipeId(), PAGE);

        assertNotNull(result);
        assertEquals(0, result.size());

        verify(restTemplate, times(1)).getForObject(anyString(), eq(String.class));
    }

    @Test
    @DisplayName("평점 추천 레시피 아이디 목록 조회 테스트")
    void testFindRecipeIdListByRatingRecommend() throws JsonProcessingException {
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn("[\"ing15\", [1]]");
        when(userService.findUserByUserId(anyLong())).thenReturn(user);

        List<Long> result = recommendService.findRecipeIdListByRatingRecommend(user.getId(), PAGE, new Long[]{BOOKMARK_ID});

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(restTemplate, times(1)).getForObject(anyString(), eq(String.class));
        verify(userService, times(1)).findUserByUserId(anyLong());
    }

    @Test
    @DisplayName("평점 추천 레시피 아이디 목록 조회 시 빈 북마크 리스트 테스트")
    void testFindRecipeIdListByRatingRecommendWithEmptyBookmark() throws JsonProcessingException {
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn("[\"ing15\", [1]]");
        when(userService.findUserByUserId(anyLong())).thenReturn(user);

        List<Long> result = recommendService.findRecipeIdListByRatingRecommend(user.getId(), PAGE, new Long[]{});

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(restTemplate, times(1)).getForObject(anyString(), eq(String.class));
        verify(userService, times(1)).findUserByUserId(anyLong());
    }

    @Test
    @DisplayName("북마크 또는 평점 추천 레시피 목록 조회 테스트")
    void testFindBookmarkOrRatingRecommendList() {
        when(userService.findUserByUserId(anyLong())).thenReturn(user);
        when(recipeService.findRecipeListByRecipeIdIn(anyList())).thenReturn(Collections.singletonList(recipe));
        when(recipeService.isRecipeBookmarked(any(User.class), any(Recipe.class))).thenReturn(true);

        RecommendRequestDto recommendRequestDto = RecommendRequestDtoTest.initRecommendRequestDto(user, recipe);
        List<RecipeListResponseDto> result = recommendService.findBookmarkOrRatingRecommendList(recommendRequestDto);

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(userService, times(1)).findUserByUserId(anyLong());
        verify(recipeService, times(1)).findRecipeListByRecipeIdIn(anyList());
        verify(recipeService, times(1)).isRecipeBookmarked(any(User.class), any(Recipe.class));
    }
}