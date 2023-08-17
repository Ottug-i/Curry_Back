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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuthServiceTest {

    private User user;
    private Token token;
    private UserSaveRequestDto userSaveRequestDto;

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
        token = new Token();
    }

    @Test
    void 회원가입() {
        // given
        userSaveRequestDto = new UserSaveRequestDto(EMAIL, NICKNAME);

        // when
        when(commonService.findByUserEmail(EMAIL)).thenThrow(new BaseException(BaseCode.USER_NOT_FOUND));
        when(userRepository.save(user)).thenReturn(user);
        when(tokenProvider.createAccessToken(user)).thenReturn(token);
        when(tokenProvider.createRefreshToken(user)).thenReturn(token);

        TokenResponseDto response = authService.login(userSaveRequestDto, mock(HttpServletResponse.class));

        // then
        assertNotNull(response);
        assertEquals(response.getEmail(), EMAIL);
        assertNotNull(response.getToken());
        verify(userRepository, times(1)).save(user);
    }

    
    @Test
    void 로그인() {
        // given
        userSaveRequestDto = new UserSaveRequestDto(EMAIL, NICKNAME);

        // when
        when(commonService.findByUserEmail(EMAIL)).thenThrow(new BaseException(BaseCode.USER_NOT_FOUND));
        when(tokenProvider.createAccessToken(user)).thenReturn(token);
        when(tokenProvider.createRefreshToken(user)).thenReturn(token);

        TokenResponseDto response = authService.login(userSaveRequestDto, mock(HttpServletResponse.class));

        // then
        assertNotNull(response);
        assertEquals(response.getEmail(), EMAIL);
        assertNotNull(response.getToken());
    }

    @Test
    void 토큰재발급() {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // when
        when(tokenRepository.findById(EMAIL)).thenReturn(Optional.of(token));
        when(tokenProvider.validateToken(token.getValue(), request)).thenReturn(true);
        when(commonService.findByUserEmail(EMAIL)).thenReturn(user);

        TokenResponseDto tokenResponse = authService.reissueToken(EMAIL, request, response);

        // then
        assertNotNull(tokenResponse);
        verify(tokenRepository, times(1)).save(token);
    }
}