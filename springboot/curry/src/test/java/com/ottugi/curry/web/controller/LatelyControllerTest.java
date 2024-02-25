package com.ottugi.curry.web.controller;

import static com.ottugi.curry.TestConstants.COMPOSITION;
import static com.ottugi.curry.TestConstants.DIFFICULTY;
import static com.ottugi.curry.TestConstants.EMAIL;
import static com.ottugi.curry.TestConstants.FAVORITE_GENRE;
import static com.ottugi.curry.TestConstants.GENRE;
import static com.ottugi.curry.TestConstants.ID;
import static com.ottugi.curry.TestConstants.INGREDIENTS;
import static com.ottugi.curry.TestConstants.NAME;
import static com.ottugi.curry.TestConstants.NICKNAME;
import static com.ottugi.curry.TestConstants.ORDERS;
import static com.ottugi.curry.TestConstants.PHOTO;
import static com.ottugi.curry.TestConstants.RECIPE_ID;
import static com.ottugi.curry.TestConstants.ROLE;
import static com.ottugi.curry.TestConstants.SERVINGS;
import static com.ottugi.curry.TestConstants.THUMBNAIL;
import static com.ottugi.curry.TestConstants.TIME;
import static com.ottugi.curry.TestConstants.USER_ID;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ottugi.curry.domain.lately.Lately;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.service.lately.LatelyService;
import com.ottugi.curry.web.dto.lately.LatelyListResponseDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class LatelyControllerTest {

    private User user;
    private Recipe recipe;
    private Lately lately;

    private MockMvc mockMvc;

    @Mock
    private LatelyService latelyService;

    @InjectMocks
    private LatelyController latelyController;

    @BeforeEach
    public void setUp() {
        user = new User(USER_ID, EMAIL, NICKNAME, FAVORITE_GENRE, ROLE);
        recipe = new Recipe(ID, RECIPE_ID, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);
        lately = new Lately();
        lately.setUser(user);
        lately.setRecipe(recipe);
        mockMvc = MockMvcBuilders.standaloneSetup(latelyController).build();
    }

    @Test
    void 최근_본_레시피_목록_조회() throws Exception {
        // given
        List<LatelyListResponseDto> latelyListResponseDtoList = new ArrayList<>();
        latelyListResponseDtoList.add(new LatelyListResponseDto(lately.getRecipeId()));
        when(latelyService.findLatelyListByUserId(anyLong())).thenReturn(latelyListResponseDtoList);

        // when, then
        mockMvc.perform(get("/api/lately/list")
                        .param("userId", String.valueOf(user.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(latelyListResponseDtoList.size())));
    }

    @Test
    void 최근_본_레시피에_따른_3D_모델_캐릭터_조회() throws Exception {
        // given
        when(latelyService.findLatelyGenreFor3DCharacter(anyLong())).thenReturn("vegetable");

        // when, then
        mockMvc.perform(get("/api/lately/character")
                        .param("userId", String.valueOf(user.getId())))
                .andExpect(status().isOk())
                .andExpect(content().string("vegetable"));
    }
}