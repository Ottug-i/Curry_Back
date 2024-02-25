package com.ottugi.curry.service.lately;

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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

import com.ottugi.curry.domain.lately.Lately;
import com.ottugi.curry.domain.lately.LatelyRepository;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.web.dto.lately.LatelyListResponseDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LatelyServiceTest {

    private User user;
    private Recipe recipe;
    private Lately lately;

    private final List<Lately> latelyList = new ArrayList<>();

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
        recipe = new Recipe(ID, RECIPE_ID, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        lately = new Lately();
        lately.setUser(user);
        lately.setRecipe(recipe);

        latelyList.add(lately);
    }

    @AfterEach
    public void clean() {
        // clean
        latelyRepository.deleteAll();
        recipeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void 최근_본_레시피_추가() {
        // given
        when(commonService.findByUserId(anyLong())).thenReturn(user);
        when(commonService.findByRecipeId(anyLong())).thenReturn(recipe);
        when(latelyRepository.findByUserIdAndRecipeId(any(User.class), any(Recipe.class))).thenReturn(null);
        when(latelyRepository.save(any(Lately.class))).thenReturn(lately);

        // when
        Boolean testResponse = latelyService.addLately(user.getId(), recipe.getRecipeId());

        // then
        assertTrue(testResponse);
    }

    @Test
    void 최근_본_레시피_목록_조회() {
        // given
        when(latelyRepository.save(any(Lately.class))).thenReturn(lately);
        when(commonService.findByUserId(anyLong())).thenReturn(user);
        when(latelyRepository.findByUserIdOrderByIdDesc(any(User.class))).thenReturn(latelyList);

        // when
        List<LatelyListResponseDto> testLatelyListResponseDtoList = latelyService.findLatelyListByUserId(user.getId());

        // then
        assertEquals(latelyList.size(), testLatelyListResponseDtoList.size());
    }

    @Test
    void 최근_본_레시피에_따른_3D_모델_캐릭터_조회() {
        // given
        when(commonService.findByUserId(anyLong())).thenReturn(user);
        when(latelyRepository.findTop1ByUserIdOrderByIdDesc(any(User.class))).thenReturn(lately);

        // when
        String testCharacter = latelyService.findLatelyGenreFor3DCharacter(user.getId());

        // then
        assertEquals("vegetable", testCharacter);
    }
}