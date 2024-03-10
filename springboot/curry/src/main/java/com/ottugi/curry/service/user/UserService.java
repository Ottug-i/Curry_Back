package com.ottugi.curry.service.user;

import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.web.dto.user.UserResponseDto;
import com.ottugi.curry.web.dto.user.UserUpdateRequestDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {
    /**
     * 회원 아이디에 따른 회원을 조회한다.
     *
     * @param userId (회원 아이디)
     * @return User (회원)
     */
    @Transactional(readOnly = true)
    User findUserByUserId(Long userId);

    /**
     * 회원 이메일에 따른 회원을 조회한다.
     *
     * @param email (회원 이메일)
     * @return User (회원)
     */
    @Transactional(readOnly = true)
    User findUserByEmail(String email);

    /**
     * 회원 아이디에 따른 회원 상세 정보를 조회한다.
     *
     * @param userId (회원 아이디)
     * @return UserResponseDto (회원 정보를 담은 DTO)
     */
    @Transactional(readOnly = true)
    UserResponseDto findUserProfileByUserId(Long userId);

    /**
     * 회원 정보를 수정한다.
     *
     * @param updateRequestDto (회원 수정 정보를 담은 DTO)
     * @return UserResponseDto (회원 정보를 담은 DTO)
     */
    UserResponseDto modifyUserProfile(UserUpdateRequestDto updateRequestDto);

    /**
     * 회원 아이디에 따라 회원을 탈퇴 처리한다.
     *
     * @param userId (회원 아이디)
     * @return boolean (회원 탈퇴 후 true)
     */
    boolean withdrawUserAccount(Long userId);
}
