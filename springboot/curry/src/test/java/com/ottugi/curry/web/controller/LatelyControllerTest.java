package com.ottugi.curry.web.controller;

import com.ottugi.curry.domain.lately.Lately;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.service.lately.LatelyService;
import com.ottugi.curry.web.dto.lately.LatelyListResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static com.ottugi.curry.TestConstants.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        when(latelyService.getLatelyAll(anyLong())).thenReturn(latelyListResponseDtoList);

        // when, then
        mockMvc.perform(get("/api/lately/list")
                        .param("userId", String.valueOf(user.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(latelyListResponseDtoList.size())));
    }

    @Test
    void 최근_본_레시피에_따른_3D_모델_캐릭터_조회() throws Exception {
        // given
        when(latelyService.getLatelyCharacter(anyLong())).thenReturn("vegetable");

        // when, then
        mockMvc.perform(get("/api/lately/character")
                    .param("userId", String.valueOf(user.getId())))
            .andExpect(status().isOk())
            .andExpect(content().string("vegetable"));
    }
}