package com.ottugi.curry.web.dto.user;

import com.ottugi.curry.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class UserResponseDto {
    @ApiModelProperty(notes = "회원 기본키", example = "1")
    private final Long id;

    @ApiModelProperty(notes = "회원 이메일", example = "curry@gmail.com")
    private final String email;

    @ApiModelProperty(notes = "회원 닉네임", example = "카레")
    private final String nickName;

    @ApiModelProperty(notes = "회원 권한", example = "일반 사용자")
    private final String role;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.role = user.getRole().getRoleName();
    }
}
