package com.ottugi.curry.service.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ottugi.curry.domain.token.Token;
import com.ottugi.curry.domain.token.TokenRepository;
import com.ottugi.curry.domain.token.TokenTest;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.domain.user.UserTest;
import com.ottugi.curry.jwt.TokenProvider;
import com.ottugi.curry.service.user.UserService;
import com.ottugi.curry.web.dto.auth.TokenResponseDto;
import com.ottugi.curry.web.dto.auth.UserSaveRequestDto;
import com.ottugi.curry.web.dto.auth.UserSaveRequestDtoTest;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    private User user;
    private UserSaveRequestDto userSaveRequestDto;
    private Token token;

    @Mock
    private UserService userService;
    @Mock
    private TokenProvider tokenProvider;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenRepository tokenRepository;
    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    public void setUp() {
        user = UserTest.initUser();
        userSaveRequestDto = UserSaveRequestDtoTest.initUserSaveRequestDto(user);
        token = TokenTest.initToken(user);
    }

    @Test
    @DisplayName("회원 가입 후 토큰 발급 테스트")
    void testSignUpAndIssueToken() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(tokenProvider.createAccessToken(any(User.class))).thenReturn(token);
        when(tokenProvider.createRefreshToken(any(User.class))).thenReturn(token);
        when(tokenRepository.save(any(Token.class))).thenReturn(token);

        TokenResponseDto result = authService.signUpOrSignInAndIssueToken(userSaveRequestDto, mock(HttpServletResponse.class));

        assertTokenResponseDto(result, user, true);

        verify(userRepository, times(1)).findByEmail(anyString());
        verify(userRepository, times(1)).save(any(User.class));
        verify(tokenProvider, times(1)).createAccessToken(any(User.class));
        verify(tokenProvider, times(1)).createRefreshToken(any(User.class));
        verify(tokenRepository, times(1)).save(any(Token.class));
    }

    @Test
    @DisplayName("로그인 후 토큰 발급 테스트")
    void testSignInAndIssueToken() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(user));
        when(tokenProvider.createAccessToken(any(User.class))).thenReturn(token);
        when(tokenProvider.createRefreshToken(any(User.class))).thenReturn(token);
        when(tokenRepository.save(any(Token.class))).thenReturn(token);

        TokenResponseDto result = authService.signUpOrSignInAndIssueToken(userSaveRequestDto, mock(HttpServletResponse.class));

        assertTokenResponseDto(result, user, false);

        verify(userRepository, times(1)).findByEmail(anyString());
        verify(tokenProvider, times(1)).createAccessToken(any(User.class));
        verify(tokenProvider, times(1)).createRefreshToken(any(User.class));
        verify(tokenRepository, times(1)).save(any(Token.class));
    }

    @Test
    @DisplayName("토큰 재발급 테스트")
    void testReissueToken() {
        when(tokenRepository.findById(any())).thenReturn(Optional.of(token));
        when(tokenProvider.validateToken(anyString(), any(HttpServletRequest.class))).thenReturn(true);
        when(userService.findUserByEmail(anyString())).thenReturn(user);
        when(tokenProvider.createAccessToken(any(User.class))).thenReturn(token);

        TokenResponseDto result = authService.reissueToken(user.getEmail(), mock(HttpServletRequest.class), mock(HttpServletResponse.class));

        assertTokenResponseDto(result, user, false);

        verify(tokenRepository, times(1)).findById(any());
        verify(tokenProvider, times(1)).validateToken(anyString(), any(HttpServletRequest.class));
        verify(userService, times(1)).findUserByEmail(anyString());
        verify(tokenProvider, times(1)).createAccessToken(any(User.class));
    }

    private void assertTokenResponseDto(TokenResponseDto result, User user, boolean isNew) {
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getNickName(), result.getNickName());
        assertNotNull(result.getToken());
        assertEquals(isNew, result.getIsNew());
    }
}