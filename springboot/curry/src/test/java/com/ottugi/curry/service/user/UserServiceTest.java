package com.ottugi.curry.service.user;

import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.web.dto.user.TokenDto;
import com.ottugi.curry.web.dto.user.UserResponseDto;
import com.ottugi.curry.web.dto.user.UserSaveRequestDto;
import com.ottugi.curry.web.dto.user.UserUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    private final String email = "wn8925@gmail.com";
    private final String nickName = "가경";
    private final String newNickName = "가경이";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration_time}")
    private int expiration_time;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(userService, "secret", secret);
        ReflectionTestUtils.setField(userService, "expiration_time", expiration_time);
    }

    @Test
    void 회원가입() {

        // given
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto(email, nickName);
        User user = new User(userSaveRequestDto.getEmail(), userSaveRequestDto.getNickName());

        // when
        when(userRepository.countByEmail(any())).thenReturn(0);
        when(userRepository.save(any())).thenReturn(user);
        TokenDto tokenDto = userService.login(userSaveRequestDto);

        // then
        assertNotNull(tokenDto);
    }

    @Test
    void 로그인() {

        // given
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto(email, nickName);
        User existingUser = new User(userSaveRequestDto.getEmail(), userSaveRequestDto.getNickName());

        // when
        when(userRepository.countByEmail(any())).thenReturn(1);
        when(userRepository.findByEmail(any())).thenReturn(existingUser);
        TokenDto tokenDto = userService.login(userSaveRequestDto);

        // then
        assertNotNull(tokenDto);
    }

    @Test
    void 회원조회() {

        // given
        User user = new User(email, nickName);

        // when
        when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));
        UserResponseDto userResponseDto = userService.getProfile(user.getId());

        // then
        assertNotNull(userResponseDto);
    }

    @Test
    void 회원수정() {

        // given
        User existingUser = new User(email, nickName);
        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto(existingUser.getId(), newNickName);

        // when
        when(userRepository.findById(existingUser.getId())).thenReturn(java.util.Optional.of(existingUser));
        UserResponseDto userResponseDto = userService.setProfile(userUpdateRequestDto);

        // then
        assertNotNull(userResponseDto);
        assertEquals(userResponseDto.getId(), userUpdateRequestDto.getId());
    }

    @Test
    void 탈퇴() {

        // given
        User user = new User(email, nickName);

        // when
        when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));
        Boolean isWithdraw = userService.setWithdraw(user.getId());

        // then
        assertTrue(isWithdraw);
    }
}