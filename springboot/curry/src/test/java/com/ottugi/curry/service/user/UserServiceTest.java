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
        when(userRepository.save(any(User.class))).thenReturn(user);
    }

    @AfterEach
    public void clean() {
        // clean
        userRepository.deleteAll();
    }

    @Test
    void 회원조회() {
        // given
        when(commonService.findByUserId(anyLong())).thenReturn(user);

        // when
        UserResponseDto testUserResponseDto = userService.getProfile(user.getId());

        // then
        assertNotNull(testUserResponseDto);
        assertEquals(user.getId(), testUserResponseDto.getId());
        assertEquals(user.getEmail(), testUserResponseDto.getEmail());
        assertEquals(user.getNickName(), testUserResponseDto.getNickName());
        assertEquals(user.getRole().getRole(), testUserResponseDto.getRole());
    }

    @Test
    void 회원수정() {
        // given
        when(commonService.findByUserId(anyLong())).thenReturn(user);

        // when
        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto(user.getId(), NEW_NICKNAME);
        UserResponseDto testUserResponseDto = userService.updateProfile(userUpdateRequestDto);

        // then
        assertNotNull(testUserResponseDto);
        assertEquals(user.getId(), testUserResponseDto.getId());
        assertEquals(user.getEmail(), testUserResponseDto.getEmail());
        assertEquals(user.getNickName(), NEW_NICKNAME);
        assertEquals(user.getRole().getRole(), testUserResponseDto.getRole());
    }

    @Test
    void 탈퇴() {
        // given
        when(commonService.findByUserId(anyLong())).thenReturn(user);

        // when
        Boolean testResponse = userService.setWithdraw(user.getId());

        // then
        assertTrue(testResponse);
    }
}