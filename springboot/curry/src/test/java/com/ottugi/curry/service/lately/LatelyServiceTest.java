package com.ottugi.curry.service.lately;

import static com.ottugi.curry.domain.recipe.RecipeTest.GENRE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ottugi.curry.domain.lately.Lately;
import com.ottugi.curry.domain.lately.LatelyRepository;
import com.ottugi.curry.domain.lately.LatelyTest;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.except.NotFoundException;
import com.ottugi.curry.service.user.UserService;
import com.ottugi.curry.web.dto.lately.LatelyListResponseDto;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LatelyServiceTest {
    private Lately lately;

    @Mock
    private UserService userService;
    @Mock
    private LatelyRepository latelyRepository;
    @InjectMocks
    private LatelyServiceImpl latelyService;

    @BeforeEach
    public void setUp() {
        lately = LatelyTest.initLately();
        lately.setUser(mock(User.class));
        lately.setRecipe(mock(Recipe.class));
    }

    @Test
    @DisplayName("최근 본 레시피 추가 테스트")
    void testAddLately() {
        when(latelyRepository.save(any(Lately.class))).thenReturn(lately);

        Boolean result = latelyService.addLately(lately.getUserId(), lately.getRecipeId());

        assertTrue(result);

        verify(latelyRepository, times(1)).save(any(Lately.class));
    }

    @Test
    @DisplayName("회원 아이디에 따른 최근 본 레시피 목록 조회")
    void testFindLatelyListByUserId() {
        when(userService.findUserByUserId(anyLong())).thenReturn(lately.getUserId());
        when(lately.getUserId().getLatelyList()).thenReturn(Collections.singletonList(lately));

        List<LatelyListResponseDto> result = latelyService.findLatelyListByUserId(lately.getUserId().getId());

        assertEquals(1, result.size());

        verify(userService, times(1)).findUserByUserId(anyLong());
    }

    @Test
    @DisplayName("3D 모델 캐릭터를 위해 회원 아이디에 따라 최근 본 레시피에 대한 장르 조회")
    void testFindLatelyGenreFor3DCharacter() {
        when(userService.findUserByUserId(anyLong())).thenReturn(lately.getUserId());
        when(latelyRepository.findTop1ByUserIdOrderByIdDesc(any(User.class))).thenReturn(lately);
        when(lately.getRecipeId().getGenre()).thenReturn(GENRE);

        String result = latelyService.findLatelyGenreFor3DCharacter(lately.getUserId().getId());

        assertEquals("vegetable", result);

        verify(userService, times(1)).findUserByUserId(anyLong());
        verify(latelyRepository, times(1)).findTop1ByUserIdOrderByIdDesc(any(User.class));
    }

    @Test
    @DisplayName("3D 모델 캐릭터를 위해 회원 아이디에 따라 최근 본 레시피에 대한 장르 조회 시 장르 조회 예외 발생 테스트")
    void testFindLatelyGenreFor3DCharacterNotFoundExcept() {
        when(userService.findUserByUserId(anyLong())).thenReturn(lately.getUserId());
        when(latelyRepository.findTop1ByUserIdOrderByIdDesc(any(User.class))).thenReturn(null);

        assertThatThrownBy(() -> latelyService.findLatelyGenreFor3DCharacter(lately.getUserId().getId()))
                .isInstanceOf(NotFoundException.class);

        verify(userService, times(1)).findUserByUserId(anyLong());
        verify(latelyRepository, times(1)).findTop1ByUserIdOrderByIdDesc(any(User.class));
    }
}