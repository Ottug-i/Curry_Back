package com.ottugi.curry.service.lately;

import com.ottugi.curry.domain.lately.Lately;
import com.ottugi.curry.domain.lately.LatelyRepository;
import com.ottugi.curry.domain.recipe.*;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.service.CommonService;
import com.ottugi.curry.web.dto.lately.LatelyListResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class LatelyServiceTest {

    private User user;

    private Recipe recipe1;
    private Recipe recipe2;

    private Lately lately1;
    private Lately lately2;

    private List<Lately> latelyList = new ArrayList<>();

    @Mock
    private CommonService commonService;

    @Mock
    private LatelyRepository latelyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private LatelyServiceImpl latelyService;

    @BeforeEach
    public void setUp() {
        // given
        user = new User(USER_ID, EMAIL, NICKNAME, FAVORITE_GENRE, ROLE);
        when(userRepository.save(eq(user))).thenReturn(user);

        recipe1 = new Recipe(ID, RECIPE_ID, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);
        when(recipeRepository.save(eq(recipe1))).thenReturn(recipe1);

        recipe2 = new Recipe(12346L, 2L, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);
        when(recipeRepository.save(eq(recipe2))).thenReturn(recipe2);

        lately1 = new Lately();
        lately1.setUser(user);
        lately1.setRecipe(recipe1);
        when(latelyRepository.save(eq(lately1))).thenReturn(lately1);

        lately2 = new Lately();
        lately2.setUser(user);
        lately2.setRecipe(recipe2);
    }

    @AfterEach
    public void clean() {
        // clean
        latelyRepository.deleteAll();
        recipeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void 최근본레시피추가() {
        // when
        when(commonService.findByUserId(user.getId())).thenReturn(user);
        when(commonService.findByRecipeId(recipe2.getRecipeId())).thenReturn(recipe2);
        when(latelyRepository.findByUserIdAndRecipeId(user, recipe2)).thenReturn(null);
        when(latelyRepository.save(any(Lately.class))).thenReturn(lately2);

        // then
        assertTrue(latelyService.addLately(user.getId(), recipe2.getRecipeId()));
    }

    @Test
    void 최근본레시피리스트조회() {
        // given
        latelyList.add(lately1);

        // when
        when(commonService.findByUserId(user.getId())).thenReturn(user);
        when(latelyRepository.findByUserIdOrderByIdDesc(user)).thenReturn(latelyList);

        List<LatelyListResponseDto> response = latelyService.getLatelyAll(user.getId());

        // then
        assertEquals(response.size(), 1);
    }
}