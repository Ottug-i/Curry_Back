package com.ottugi.curry.web.dto.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ottugi.curry.ValidatorUtil;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserSaveRequestDtoTest {
    private final ValidatorUtil<UserSaveRequestDto> validatorUtil = new ValidatorUtil<>();
    public static final String INVALID_EMAIL = "1234.gmail.com";
    public static final String INVALID_BLANK = "";

    public static UserSaveRequestDto initUserSaveRequestDto(User user) {
        return UserSaveRequestDto.builder()
                .email(user.getEmail())
                .nickName(user.getNickName())
                .build();
    }

    private User user;

    @BeforeEach
    public void setUp() {
        user = UserTest.initUser();
    }

    @Test
    @DisplayName("UserSaveRequestDto 생성 테스트")
    void testUserSaveRequestDto() {
        UserSaveRequestDto userSaveRequestDto = initUserSaveRequestDto(user);

        assertEquals(user.getEmail(), userSaveRequestDto.getEmail());
        assertEquals(user.getNickName(), userSaveRequestDto.getNickName());
    }

    @Test
    @DisplayName("UserSaveRequestDto toEntity 생성 테스트")
    void testUserSaveRequestDtoEntity() {
        UserSaveRequestDto userSaveRequestDto = initUserSaveRequestDto(user);

        User userEntity = userSaveRequestDto.toEntity();

        assertNotNull(userEntity);
        assertEquals(userSaveRequestDto.getEmail(), userEntity.getEmail());
        assertEquals(userSaveRequestDto.getNickName(), userEntity.getNickName());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto();

        assertNotNull(userSaveRequestDto);
        assertNull(userSaveRequestDto.getEmail());
        assertNull(userSaveRequestDto.getNickName());
    }

    @Test
    @DisplayName("이메일 유효성 검증 테스트")
    void email_validation() {
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .email(INVALID_EMAIL)
                .nickName(user.getNickName())
                .build();

        validatorUtil.validate(userSaveRequestDto);
    }

    @Test
    @DisplayName("닉네임 유효성 검증 테스트")
    void nickName_validation() {
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .email(user.getEmail())
                .nickName(INVALID_BLANK)
                .build();

        validatorUtil.validate(userSaveRequestDto);
    }
}