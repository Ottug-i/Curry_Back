package com.ottugi.curry.service.user;

import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.service.CommonService;
import com.ottugi.curry.web.dto.user.UserResponseDto;
import com.ottugi.curry.web.dto.user.UserUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ottugi.curry.TestConstants.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    private User user;

    @Mock
    private CommonService commonService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        // given
        user = new User();
    }

    @Test
    void 회원조회() {
        // when
        when(commonService.findByUserId(USER_ID)).thenReturn(user);

        UserResponseDto response = userService.getProfile(USER_ID);

        // then
        assertEquals(response.getId(), USER_ID);
    }

    @Test
    void 회원수정() {
        // given
        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto(USER_ID, NEW_NICKNAME);

        // when
        when(commonService.findByUserId(USER_ID)).thenReturn(user);

        UserResponseDto response = userService.updateProfile(userUpdateRequestDto);

        // then
        assertEquals(response.getNickName(), NEW_NICKNAME);
    }

    @Test
    void 탈퇴() {
        // when
        when(commonService.findByUserId(USER_ID)).thenReturn(user);

        // then
        assertTrue(userService.setWithdraw(USER_ID));
    }
}