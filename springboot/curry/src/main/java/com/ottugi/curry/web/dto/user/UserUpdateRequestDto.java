package com.ottugi.curry.web.dto.user;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateRequestDto {
    @NotNull
    @ApiModelProperty(notes = "회원 기본키", example = "1", required = true)
    private Long id;

    @NotBlank
    @ApiModelProperty(notes = "회원 닉네임", example = "가경이", required = true)
    private String nickName;

    @Builder
    public UserUpdateRequestDto(Long id, String nickName) {
        this.id = id;
        this.nickName = nickName;
    }
}
