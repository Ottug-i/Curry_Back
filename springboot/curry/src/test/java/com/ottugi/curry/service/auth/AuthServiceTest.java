package com.ottugi.curry.service.auth;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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
import com.ottugi.curry.except.JwtAuthenticationException;
import com.ottugi.curry.jwt.TokenProvider;
import com.ottugi.curry.jwt.TokenValidator;
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
    private Token token;

    @Mock
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenProvider tokenProvider;
    @Mock
    private TokenValidator tokenValidator;
    @Mock
    private TokenRepository tokenRepository;
    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    public void setUp() {
        user = UserTest.initUser();
        token = TokenTest.initToken(user);
    }

    @Test
    @DisplayName("회원 가입 후 토큰 발급 테스트")
    void testSignUpAndIssueToken() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(tokenProvider.createAccessToken(any(User.class))).thenReturn(token);
        when(tokenProvider.createRefreshToken(any(User.class))).thenReturn(token);
        when(tokenRepository.save(any(Token.class))).thenReturn(token);

        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDtoTest.initUserSaveRequestDto(user);
        TokenResponseDto result = authService.signUpOrSignInAndIssueToken(userSaveRequestDto, mock(HttpServletResponse.class));

        assertTokenResponseDto(result, true);

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

        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDtoTest.initUserSaveRequestDto(user);
        TokenResponseDto result = authService.signUpOrSignInAndIssueToken(userSaveRequestDto, mock(HttpServletResponse.class));

        assertTokenResponseDto(result, false);

        verify(userRepository, times(1)).findByEmail(anyString());
        verify(tokenProvider, times(1)).createAccessToken(any(User.class));
        verify(tokenProvider, times(1)).createRefreshToken(any(User.class));
        verify(tokenRepository, times(1)).save(any(Token.class));
    }

    @Test
    @DisplayName("토큰 재발급 테스트")
    void testReissueToken() {
        when(tokenRepository.findById(any())).thenReturn(Optional.of(token));
        when(tokenValidator.validateToken(anyString(), any(HttpServletRequest.class))).thenReturn(true);
        when(userService.findUserByEmail(anyString())).thenReturn(user);
        when(tokenProvider.createAccessToken(any(User.class))).thenReturn(token);

        TokenResponseDto result = authService.reissueToken(user.getEmail(), mock(HttpServletRequest.class), mock(HttpServletResponse.class));

        assertTokenResponseDto(result, true);

        verify(tokenRepository, times(1)).findById(any());
        verify(tokenValidator, times(1)).validateToken(anyString(), any(HttpServletRequest.class));
        verify(userService, times(1)).findUserByEmail(anyString());
        verify(tokenProvider, times(1)).createAccessToken(any(User.class));
    }

    @Test
    @DisplayName("토큰 재발급 시 리프레쉬 토큰 만료 예외 발생 테스트")
    void testReissueTokenJwtAuthenticationExcept() {
        when(tokenRepository.findById(any())).thenReturn(Optional.of(token));
        when(tokenValidator.validateToken(anyString(), any(HttpServletRequest.class))).thenReturn(false);

        assertThatThrownBy(() -> authService.reissueToken(user.getEmail(), mock(HttpServletRequest.class), mock(HttpServletResponse.class)))
                .isInstanceOf(JwtAuthenticationException.class);

        verify(tokenRepository, times(1)).findById(any());
        verify(tokenValidator, times(1)).validateToken(anyString(), any(HttpServletRequest.class));
    }

    private void assertTokenResponseDto(TokenResponseDto resultDto, boolean isNew) {
        assertNotNull(resultDto);
        assertEquals(user.getId(), resultDto.getId());
        assertEquals(user.getEmail(), resultDto.getEmail());
        assertEquals(user.getNickName(), resultDto.getNickName());
        assertNotNull(resultDto.getToken());
        assertEquals(isNew, resultDto.getIsNew());
    }
}