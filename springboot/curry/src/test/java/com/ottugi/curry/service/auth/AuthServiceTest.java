package com.ottugi.curry.service.auth;

import com.ottugi.curry.domain.token.Token;
import com.ottugi.curry.domain.token.TokenRepository;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.except.BaseCode;
import com.ottugi.curry.except.BaseException;
import com.ottugi.curry.jwt.TokenProvider;
import com.ottugi.curry.service.CommonService;
import com.ottugi.curry.web.dto.auth.TokenResponseDto;
import com.ottugi.curry.web.dto.auth.UserSaveRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletResponse;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthServiceTest {

    private User user;

    @Mock
    private CommonService commonService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        // given
        user = new User();
    }

    @Test
    void 회원가입() {
        // given
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto(EMAIL, NICKNAME);

        // when
        when(commonService.findByUserEmail(EMAIL)).thenThrow(new BaseException(BaseCode.USER_NOT_FOUND));
        when(tokenProvider.createAccessToken(user)).thenReturn(new Token(EMAIL, VALUE, EXPIRED_TIME));
        when(tokenProvider.createRefreshToken(user)).thenReturn(new Token(EMAIL, VALUE, EXPIRED_TIME));

        TokenResponseDto response = authService.login(userSaveRequestDto, mock(HttpServletResponse.class));

        // then
        assertNotNull(response);
        assertEquals(response.getEmail(), EMAIL);
        assertNotNull(response.getToken());
    }

    
    @Test
    void 로그인() {
    }

    @Test
    void 토큰재발급() {
    }
}