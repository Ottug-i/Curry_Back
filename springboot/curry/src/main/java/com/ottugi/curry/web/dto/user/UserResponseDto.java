package com.ottugi.curry.web.dto.user;

import com.ottugi.curry.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class UserResponseDto {

    @ApiModelProperty(notes = "회원 기본키", example = "1")
    private Long id;

    @ApiModelProperty(notes = "회원 이메일", example = "wn8925@sookmyung.ac.kr")
    private String email;

    @ApiModelProperty(notes = "회원 닉네임", example = "가경")
    private String nickName;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickName = user.getNickName();
    }
}
