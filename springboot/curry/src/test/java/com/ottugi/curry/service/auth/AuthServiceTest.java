package com.ottugi.curry.service.auth;

import static com.ottugi.curry.TestConstants.EMAIL;
import static com.ottugi.curry.TestConstants.EXPIRED_TIME;
import static com.ottugi.curry.TestConstants.FAVORITE_GENRE;
import static com.ottugi.curry.TestConstants.IS_NEW;
import static com.ottugi.curry.TestConstants.NICKNAME;
import static com.ottugi.curry.TestConstants.ROLE;
import static com.ottugi.curry.TestConstants.USER_ID;
import static com.ottugi.curry.TestConstants.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.ottugi.curry.domain.token.Token;
import com.ottugi.curry.domain.token.TokenRepository;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.jwt.TokenProvider;
import com.ottugi.curry.web.dto.auth.TokenResponseDto;
import com.ottugi.curry.web.dto.auth.UserSaveRequestDto;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthServiceTest {

    private User user;
    private Token token;

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
        token = new Token(EMAIL, VALUE, EXPIRED_TIME);
    }

    @AfterEach
    public void clean() {
        // clean
        tokenRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void 회원_가입() {
        // given
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(tokenProvider.createAccessToken(any(User.class))).thenReturn(token);
        when(tokenProvider.createRefreshToken(any(User.class))).thenReturn(token);
        when(tokenRepository.save(any(Token.class))).thenReturn(token);

        // when
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto(user.getEmail(), user.getNickName());
        TokenResponseDto testTokenResponseDto = authService.singUpOrLogin(userSaveRequestDto, mock(HttpServletResponse.class));

        // then
        assertNotNull(testTokenResponseDto);
        assertEquals(user.getId(), testTokenResponseDto.getId());
        assertEquals(user.getEmail(), testTokenResponseDto.getEmail());
        assertEquals(user.getNickName(), testTokenResponseDto.getNickName());
        assertNotNull(testTokenResponseDto.getToken());
        assertEquals(IS_NEW, testTokenResponseDto.getIsNew());
    }

    @Test
    void 회원_로그인() {
        // given
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        when(commonService.findByUserEmail(anyString())).thenReturn(user);
        when(tokenProvider.createAccessToken(any(User.class))).thenReturn(token);
        when(tokenProvider.createRefreshToken(any(User.class))).thenReturn(token);
        when(tokenRepository.save(any(Token.class))).thenReturn(token);

        // when
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto(user.getEmail(), user.getNickName());
        TokenResponseDto testTokenResponseDto = authService.singUpOrLogin(userSaveRequestDto, mock(HttpServletResponse.class));

        // then
        assertNotNull(testTokenResponseDto);
        assertEquals(user.getId(), testTokenResponseDto.getId());
        assertEquals(user.getEmail(), testTokenResponseDto.getEmail());
        assertEquals(user.getNickName(), testTokenResponseDto.getNickName());
        assertNotNull(testTokenResponseDto.getToken());
        assertEquals(!IS_NEW, testTokenResponseDto.getIsNew());
    }

    @Test
    void 토큰_재발급() {
        // given
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(tokenRepository.findById(anyString())).thenReturn(Optional.of(token));
        when(tokenProvider.validateToken(anyString(), any(HttpServletRequest.class))).thenReturn(true);
        when(commonService.findByUserEmail(anyString())).thenReturn(user);
        when(tokenProvider.createAccessToken(any(User.class))).thenReturn(token);

        // when
        TokenResponseDto testTokenResponseDto = authService.reissueToken(user.getEmail(), mock(HttpServletRequest.class),
                mock(HttpServletResponse.class));

        // then
        assertNotNull(testTokenResponseDto);
        assertNotNull(testTokenResponseDto.getToken());
    }
}