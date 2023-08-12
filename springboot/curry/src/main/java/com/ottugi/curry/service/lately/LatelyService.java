package com.ottugi.curry.service.lately;

import com.ottugi.curry.web.dto.lately.LatelyListResponseDto;

import java.util.List;

public interface LatelyService {
    Boolean addLately(Long userId, Long recipeId);
    List<LatelyListResponseDto> getLatelyAll(Long userId);
}
