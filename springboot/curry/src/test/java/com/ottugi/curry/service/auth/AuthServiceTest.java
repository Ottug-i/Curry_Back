package com.ottugi.curry.service.auth;

import com.ottugi.curry.domain.token.Token;
import com.ottugi.curry.domain.token.TokenRepository;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.jwt.TokenProvider;
import com.ottugi.curry.service.CommonService;
import com.ottugi.curry.web.dto.auth.TokenResponseDto;
import com.ottugi.curry.web.dto.auth.UserSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
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
    private User newUser;

    private Token token;
    private Token newToken;
    
    private UserSaveRequestDto userSaveRequestDto;
    private TokenResponseDto tokenResponseDto;

    private final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    private final HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

    @Mock
    private CommonService commonService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    public void setUp() {
        // given
        user = new User(USER_ID, EMAIL, NICKNAME, FAVORITE_GENRE, ROLE);
        when(userRepository.save(eq(user))).thenReturn(user);

        newUser = new User(NEW_USER_ID, NEW_EMAIL, NICKNAME, FAVORITE_GENRE, ROLE);

        token = new Token(EMAIL, VALUE, EXPIRED_TIME);
        when(tokenRepository.save(eq(token))).thenReturn(token);

        newToken = new Token(NEW_EMAIL, VALUE, EXPIRED_TIME);

        userSaveRequestDto = new UserSaveRequestDto(newUser.getEmail(), newUser.getNickName());
    }

    @AfterEach
    public void clean() {
        // clean
        tokenRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void 회원가입() {
        // given
        userSaveRequestDto = new UserSaveRequestDto(newUser.getEmail(), newUser.getNickName());

        // when
        when(userRepository.existsByEmail(newUser.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(newUser);
        when(tokenProvider.createAccessToken(newUser)).thenReturn(newToken);
        when(tokenProvider.createRefreshToken(newUser)).thenReturn(newToken);
        when(tokenRepository.save(eq(newToken))).thenReturn(newToken);

        tokenResponseDto = authService.login(userSaveRequestDto, httpServletResponse);

        // then
        assertNotNull(tokenResponseDto);
        assertEquals(tokenResponseDto.getEmail(), newUser.getEmail());
        assertNotNull(tokenResponseDto.getToken());
        assertEquals(tokenResponseDto.getIsNew(), IS_NEW);
    }
    
    @Test
    void 로그인() {
        // given
        userSaveRequestDto = new UserSaveRequestDto(user.getEmail(), user.getNickName());

        // when
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
        when(commonService.findByUserEmail(user.getEmail())).thenReturn(user);
        when(tokenProvider.createAccessToken(user)).thenReturn(token);
        when(tokenProvider.createRefreshToken(user)).thenReturn(token);
        when(tokenRepository.save(eq(token))).thenReturn(token);

        tokenResponseDto = authService.login(userSaveRequestDto, httpServletResponse);

        // then
        assertNotNull(tokenResponseDto);
        assertEquals(tokenResponseDto.getEmail(), user.getEmail());
        assertNotNull(tokenResponseDto.getToken());
        assertEquals(tokenResponseDto.getIsNew(), !IS_NEW);
    }

    @Test
    void 토큰재발급() {
        // when
        when(tokenRepository.findById(user.getEmail())).thenReturn(Optional.of(token));
        when(tokenProvider.validateToken(token.getValue(), httpServletRequest)).thenReturn(true);
        when(commonService.findByUserEmail(user.getEmail())).thenReturn(user);
        when(tokenProvider.createAccessToken(user)).thenReturn(token);

        tokenResponseDto = authService.reissueToken(user.getEmail(), httpServletRequest, httpServletResponse);

        // then
        assertNotNull(tokenResponseDto);
    }
}