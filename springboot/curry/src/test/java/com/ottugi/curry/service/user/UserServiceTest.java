package com.ottugi.curry.service.user;

import static com.ottugi.curry.domain.user.UserTest.NEW_NICKNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.domain.user.UserTest;
import com.ottugi.curry.web.dto.user.UserResponseDto;
import com.ottugi.curry.web.dto.user.UserUpdateRequestDto;
import com.ottugi.curry.web.dto.user.UserUpdateRequestDtoTest;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private User user;

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        user = UserTest.initUser();
    }

    @Test
    @DisplayName("회원 아이디로 회원 조회 테스트")
    void testFindUserByUserId() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

        User result = userService.findUserByUserId(user.getId());

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getNickName(), result.getNickName());
        assertEquals(user.getRole(), result.getRole());

        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("회원 이메일로 회원 조회 테스트")
    void testFindUserByEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(user));

        User result = userService.findUserByEmail(user.getEmail());

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getNickName(), result.getNickName());
        assertEquals(user.getRole(), result.getRole());

        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    @DisplayName("회원 아이디로 회원 프로필 조회 테스트")
    void testFindUserProfileByUserId() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

        UserResponseDto result = userService.findUserProfileByUserId(user.getId());

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getNickName(), result.getNickName());
        assertEquals(user.getRole().getRole(), result.getRole());

        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("회원 프로필 수정 테스트")
    void testModifyUserProfile() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDtoTest.initUserUpdateRequestDto(user);
        UserResponseDto result = userService.modifyUserProfile(userUpdateRequestDto);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getNickName(), NEW_NICKNAME);
        assertEquals(user.getRole().getRole(), result.getRole());

        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("회원 아이디로 회원 탈퇴 테스트")
    void testWithdrawUserAccount() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

        Boolean result = userService.withdrawUserAccount(user.getId());

        assertTrue(result);

        verify(userRepository, times(1)).findById(anyLong());
    }
}