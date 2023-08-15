package com.ottugi.curry.service.recommend;

import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.service.CommonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecommendServiceTest {

    private User user;
    private Recipe recipe;
    private Bookmark bookmark;

    @Mock
    private CommonService commonService;

    @InjectMocks
    private RecommendService recommendService;

    @BeforeEach
    public void setUp() {
        // given
        user = new User();
        recipe = new Recipe();
        bookmark = new Bookmark();
    }

    @Test
    void 랜덤레시피목록조회() {
    }

    @Test
    void 평점조회() {
    }

    @Test
    void 평점업데이트() {
    }

    @Test
    void 평점삭제() {
    }

    @Test
    void 재료추천목록조회() {
    }

    @Test
    void 북마크추천아이디목록조회() {
    }

    @Test
    void 평점추천목록아이디조회() {
    }

    @Test
    void 북마크및평점레시피목록조회() {
    }
}