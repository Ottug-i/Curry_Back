package com.ottugi.curry.jwt;

import com.ottugi.curry.config.GlobalConfig;
import com.ottugi.curry.domain.token.Token;
import com.ottugi.curry.domain.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class TokenProvider {
    private static final long ACCESS_TOKEN_VALID_TIME = Duration.ofMinutes(30).toMillis();
    private static final long REFRESH_TOKEN_VALID_TIME = Duration.ofDays(14).toMillis();

    private String secretKey;
    private final String jwtHeader;
    private final String jwtTokenPrefix;

    public TokenProvider(GlobalConfig config) {
        this.secretKey = config.getJwtKey();
        this.jwtHeader = config.getJwtHeader();
        this.jwtTokenPrefix = config.getJwtPrefix();
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public Token createAccessToken(User user) {
        return createToken(user, ACCESS_TOKEN_VALID_TIME);
    }

    public Token createRefreshToken(User user) {
        return createToken(user, REFRESH_TOKEN_VALID_TIME);
    }

    public Token createToken(User user, long tokenValidTime) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("roles", user.getRole());
        Date now = new Date();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return Token.builder()
                .key(user.getEmail())
                .value(token)
                .expiredTime(tokenValidTime)
                .build();
    }

    public Authentication getAuthentication(String token) {
        return new UsernamePasswordAuthenticationToken(getUserEmail(token), token, getUserRole(token));
    }

    public String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtHeader);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtTokenPrefix)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader(jwtHeader, accessToken);
    }

    private String getUserEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    private List<GrantedAuthority> getUserRole(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(claims.get("roles", String.class)));
        return roles;
    }
}