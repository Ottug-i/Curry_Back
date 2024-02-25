package com.ottugi.curry.service.lately;

import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.web.dto.lately.LatelyListResponseDto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LatelyService {
    Boolean addLately(User user, Recipe recipe);

    @Transactional(readOnly = true)
    List<LatelyListResponseDto> findLatelyListByUserId(Long userId);

    @Transactional(readOnly = true)
    String findLatelyGenreFor3DCharacter(Long userId);
}
