package com.ottugi.curry.web.dto.auth;

import com.ottugi.curry.domain.user.Role;
import com.ottugi.curry.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSaveRequestDto {
    @Email
    @ApiModelProperty(notes = "회원 이메일", example = "wn8926@sookmyung.ac.kr", required = true)
    private String email;

    @NotBlank
    @ApiModelProperty(notes = "회원 닉네임", example = "가경이", required = true)
    private String nickName;

    @Builder
    public UserSaveRequestDto(String email, String nickName) {
        this.email = email;
        this.nickName = nickName;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .nickName(nickName)
                .favoriteGenre(null)
                .role(Role.USER)
                .build();
    }
}
