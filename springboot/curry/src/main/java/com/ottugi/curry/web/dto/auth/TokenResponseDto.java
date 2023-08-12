package com.ottugi.curry.web.dto.auth;

import com.ottugi.curry.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenResponseDto {

    @ApiModelProperty(notes = "회원 기본키", example = "1")
    private Long id;

    @ApiModelProperty(notes = "회원 이메일", example = "wn8925@sookmyung.ac.kr")
    private String email;

    @ApiModelProperty(notes = "회원 닉네임", example = "가경")
    private String nickName;

    @ApiModelProperty(notes = "회원 권한", example = "일반 사용자")
    private String role;

    @ApiModelProperty(notes = "회원 토큰", example = "secret")
    private String token;

    public TokenResponseDto(User user, String token) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.token = token;
        this.role = user.getRole().getRole();
    }
}
