package com.ottugi.curry.service.lately;

import com.ottugi.curry.domain.lately.Lately;
import com.ottugi.curry.domain.lately.LatelyRepository;
import com.ottugi.curry.domain.recipe.*;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.service.CommonService;
import com.ottugi.curry.web.dto.lately.LatelyListResponseDto;
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
    private Recipe recipe;
    private Lately lately;
    private List<Lately> latelyList = new ArrayList<>();

    @Mock
    private CommonService commonService;

    @Mock
    private LatelyRepository latelyRepository;

    @InjectMocks
    private LatelyService latelyService;

    @BeforeEach
    public void setUp() {
        // given
        user = new User();
        recipe = new Recipe();
        lately = new Lately();
    }

    @Test
    void 최근본레시피추가() {
        // when
        when(commonService.findByUserId(USER_ID)).thenReturn(user);
        when(commonService.findByRecipeId(EXIST_RECIPE_ID)).thenReturn(recipe);
        when(latelyRepository.findByUserIdAndRecipeId(user, recipe)).thenReturn(null);
        when(latelyRepository.save(lately)).thenReturn(lately);

        // then
        assertTrue(latelyService.addLately(USER_ID, EXIST_RECIPE_ID));
        verify(latelyRepository, times(1)).save(lately);
    }

    @Test
    void 최근본레시피리스트조회() {
        // when
        when(commonService.findByUserId(USER_ID)).thenReturn(user);
        when(latelyRepository.findByUserIdOrderByIdDesc(user)).thenReturn(latelyList);

        List<LatelyListResponseDto> response = latelyService.getLatelyAll(USER_ID);

        // then
        assertEquals(response.size(), 0);
    }
}