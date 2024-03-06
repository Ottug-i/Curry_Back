package com.ottugi.curry.web.dto.user;

import static com.ottugi.curry.domain.user.UserTest.NEW_NICKNAME;
import static com.ottugi.curry.web.dto.auth.UserSaveRequestDtoTest.INVALID_BLANK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ottugi.curry.ValidatorUtil;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserUpdateRequestDtoTest {
    private final ValidatorUtil<UserUpdateRequestDto> validatorUtil = new ValidatorUtil<>();

    public static UserUpdateRequestDto initUserUpdateRequestDto(User user) {
        return UserUpdateRequestDto.builder()
                .id(user.getId())
                .nickName(NEW_NICKNAME)
                .build();
    }

    private User user;

    @BeforeEach
    public void setUp() {
        user = UserTest.initUser();
    }

    @Test
    @DisplayName("UserUpdateRequestDto 생성 테스트")
    void testUserUpdateRequestDto() {
        UserUpdateRequestDto userUpdateRequestDto = initUserUpdateRequestDto(user);

        assertEquals(user.getId(), userUpdateRequestDto.getId());
        assertEquals(NEW_NICKNAME, userUpdateRequestDto.getNickName());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto();

        assertNotNull(userUpdateRequestDto);
        assertNull(userUpdateRequestDto.getId());
        assertNull(userUpdateRequestDto.getNickName());
    }

    @Test
    @DisplayName("회원 아이디 유효성 검증 테스트")
    void userId_validation() {
        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder()
                .id(null)
                .nickName(NEW_NICKNAME)
                .build();

        validatorUtil.validate(userUpdateRequestDto);
    }

    @Test
    @DisplayName("닉네임 유효성 검증 테스트")
    void nickName_validation() {
        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder()
                .id(user.getId())
                .nickName(INVALID_BLANK)
                .build();

        validatorUtil.validate(userUpdateRequestDto);
    }
}