package com.ottugi.curry.service.user;

import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.service.CommonService;
import com.ottugi.curry.web.dto.user.UserResponseDto;
import com.ottugi.curry.web.dto.user.UserUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
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

    private UserUpdateRequestDto userUpdateRequestDto;

    @Mock
    private CommonService commonService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        // given
        user = new User(USER_ID, EMAIL, NICKNAME, FAVORITE_GENRE, ROLE);
        when(userRepository.save(eq(user))).thenReturn(user);
    }

    @AfterEach
    public void clean() {
        // clean
        userRepository.deleteAll();
    }

    @Test
    void 회원조회() {
        // when
        when(commonService.findByUserId(user.getId())).thenReturn(user);

        UserResponseDto response = userService.getProfile(user.getId());

        // then
        assertEquals(response.getId(), user.getId());
    }

    @Test
    void 회원수정() {
        // given
        userUpdateRequestDto = new UserUpdateRequestDto(user.getId(), NEW_NICKNAME);

        // when
        when(commonService.findByUserId(user.getId())).thenReturn(user);

        UserResponseDto response = userService.updateProfile(userUpdateRequestDto);

        // then
        assertEquals(response.getNickName(), NEW_NICKNAME);
    }

    @Test
    void 탈퇴() {
        // when
        when(commonService.findByUserId(user.getId())).thenReturn(user);

        // then
        assertTrue(userService.setWithdraw(user.getId()));
    }
}