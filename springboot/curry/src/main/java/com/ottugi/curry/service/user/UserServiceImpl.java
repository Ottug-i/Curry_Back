package com.ottugi.curry.service.user;

import com.ottugi.curry.domain.user.*;
import com.ottugi.curry.service.CommonService;
import com.ottugi.curry.web.dto.user.UserResponseDto;
import com.ottugi.curry.web.dto.user.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CommonService commonService;

    // 회원 정보 조회
    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getProfile(Long id) {
        User user = commonService.findByUserId(id);
        return new UserResponseDto(user);
    }

    // 회원 정보 수정
    @Override
    @Transactional
    public UserResponseDto updateProfile(UserUpdateRequestDto userUpdateRequestDto) {
        User user = commonService.findByUserId(userUpdateRequestDto.getId());
        user.updateProfile(userUpdateRequestDto.getNickName());
        return new UserResponseDto(user);
    }

    // 회원 탈퇴
    @Override
    @Transactional
    public Boolean setWithdraw(Long id) {
        User user = commonService.findByUserId(id);
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }
}
