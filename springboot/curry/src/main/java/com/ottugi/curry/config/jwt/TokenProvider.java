package com.ottugi.curry.config.jwt;

import com.ottugi.curry.domain.token.Token;
import com.ottugi.curry.domain.token.TokenRepository;
import com.ottugi.curry.domain.user.User;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.*;

@Slf4j
@Component
public class TokenProvider {

    @Value("${jwt.security.key}")
    private String secretKey;

    @Value("${jwt.response.header}")
    private String jwtHeader;

    @Value("${jwt.token.prefix}")
    private String jwtTokenPrefix;

    private long accessTokenValidTime = Duration.ofMinutes(30).toMillis();
    private long refreshTokenValidTime = Duration.ofDays(14).toMillis();

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public Token createAccessToken(User user) {
        return createToken(user, accessTokenValidTime);
    }

    public Token createRefreshToken(User user) {
        return createToken(user, refreshTokenValidTime);
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

    public String getUserEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public List<GrantedAuthority> getUserRole(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(claims.get("roles", String.class)));
        return roles;
    }

    public String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtHeader);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtTokenPrefix)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token, HttpServletRequest request) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch(SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature");
            return false;
        } catch(UnsupportedJwtException e) {
            log.error("Unsupported JWT token");
            return false;
        } catch(IllegalArgumentException e) {
            log.error("JWT token is invalid");
            return false;
        }
    }

    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader(jwtHeader, accessToken);
    }
}