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
import static org.mockito.Mockito.when;

@SpringBootTest
class LatelyServiceTest {

    private User user;
    private Recipe recipe;
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
    }

    @Test
    void 최근본레시피추가() {
        // when
        when(commonService.findByUserId(USER_ID)).thenReturn(user);
        when(commonService.findByRecipeId(RECIPE_ID)).thenReturn(recipe);
        when(latelyRepository.findByUserIdAndRecipeId(user, recipe)).thenReturn(null);

        // then
        assertTrue(latelyService.addLately(USER_ID, RECIPE_ID));
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