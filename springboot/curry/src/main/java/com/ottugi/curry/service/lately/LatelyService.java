package com.ottugi.curry.service.lately;

import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.web.dto.lately.LatelyListResponseDto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LatelyService {
    /**
     * 회원 별 최근 본 레시피를 추가한다.
     *
     * @param user   (회원)
     * @param recipe (레시피)
     */
    void addLately(User user, Recipe recipe);

    /**
     * 회원 아이디에 따른 최근 본 레시피 목록을 조회한다.
     *
     * @param userId (회원 아이디)
     * @return List<LatelyListResponseDto> (최근 본 레시피 목록 정보를 담은 DTO 리스트)
     */
    @Transactional(readOnly = true)
    List<LatelyListResponseDto> findLatelyListByUserId(Long userId);

    /**
     * 3D 메타 캐릭터 선택 전환을 위해 회원 아이디에 따른 최근 본 레시피의 메인 장르의 3D 메타 모델 캐릭터 모델 종류를 조회한다.
     *
     * @param userId (회원 아이디)
     * @return String (최근 본 레시피의 메인 장르)
     */
    @Transactional(readOnly = true)
    String findLatelyMainGenreCharacterFor3DCharacter(Long userId);
}
