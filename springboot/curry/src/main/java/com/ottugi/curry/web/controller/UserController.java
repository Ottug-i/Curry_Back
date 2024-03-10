package com.ottugi.curry.web.controller;

import com.ottugi.curry.service.user.UserService;
import com.ottugi.curry.web.dto.user.UserResponseDto;
import com.ottugi.curry.web.dto.user.UserUpdateRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"User API (회원 API)"})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    @ApiOperation(value = "회원 정보 조회", notes = "회원 정보를 리턴합니다.")
    @ApiImplicitParam(name = "id", value = "회원 기본키", example = "1", required = true)
    public ResponseEntity<UserResponseDto> userProfileDetails(@RequestParam @NotNull Long id) {
        return ResponseEntity.ok().body(userService.findUserProfileByUserId(id));
    }

    @PutMapping
    @ApiOperation(value = "회원 정보 수정", notes = "회원 정보 수정를 수정한 후, 수정된 회원 정보를 리턴합니다.")
    public ResponseEntity<UserResponseDto> userProfileModify(@RequestBody @Valid UserUpdateRequestDto requestDto) {
        return ResponseEntity.ok().body(userService.modifyUserProfile(requestDto));
    }

    @DeleteMapping("/withdraw")
    @ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴를 한 후, true를 리턴합니다.")
    @ApiImplicitParam(name = "id", value = "회원 기본키", example = "1", required = true)
    public ResponseEntity<Boolean> userWithdraw(@RequestParam @NotNull Long id) {
        return ResponseEntity.ok().body(userService.withdrawUserAccount(id));
    }
}
