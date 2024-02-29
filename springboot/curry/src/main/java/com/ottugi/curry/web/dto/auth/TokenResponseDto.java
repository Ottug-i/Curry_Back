package com.ottugi.curry.web.dto.auth;

import com.ottugi.curry.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class TokenResponseDto {
    @ApiModelProperty(notes = "회원 기본키", example = "1")
    private final Long id;

    @ApiModelProperty(notes = "회원 이메일", example = "wn8925@sookmyung.ac.kr")
    private final String email;

    @ApiModelProperty(notes = "회원 닉네임", example = "가경")
    private final String nickName;

    @ApiModelProperty(notes = "회원 권한", example = "일반 사용자")
    private final String role;

    @ApiModelProperty(notes = "회원 토큰", example = "secret")
    private final String token;

    @ApiModelProperty(notes = "새로운 가입 여부", example = "true")
    private final Boolean isNew;

    public TokenResponseDto(User user, String token) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.token = token;
        this.role = user.getRole().getRole();
        this.isNew = user.getIsNew();
    }
}
