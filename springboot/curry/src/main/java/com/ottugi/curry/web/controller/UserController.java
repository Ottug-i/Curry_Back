package com.ottugi.curry.web.controller;

import com.ottugi.curry.service.user.UserService;
import com.ottugi.curry.web.dto.user.TokenDto;
import com.ottugi.curry.web.dto.user.UserResponseDto;
import com.ottugi.curry.web.dto.user.UserSaveRequestDto;
import com.ottugi.curry.web.dto.user.UserUpdateRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags={"User API (회원 API)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/login")
    @ApiOperation(value = "소셜 회원가입과 로그인", notes = "소셜 회원가입 또는 로그인을 한 후, jwt 토큰과 사용자 정보를 리턴합니다.")
    public ResponseEntity<TokenDto> login(@RequestBody UserSaveRequestDto userSaveRequestDto, HttpServletResponse response) {
        return ResponseEntity.ok().body(userService.login(userSaveRequestDto, response));
    }

    @PostMapping("/auth/reissue")
    @ApiOperation(value = "토큰 재발급", notes = "재발급된 토큰 정보를 리턴합니다.")
    @ApiImplicitParam(name = "email", value = "회원 이메일", example = "wn8925@sookmyung.ac.kr", required = true)
    public ResponseEntity<TokenDto> reissue(@RequestParam String email, HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok().body(userService.reissue(email, request, response));
    }

    @GetMapping("/api/user/getProfile")
    @ApiOperation(value = "회원 정보", notes = "회원 정보를 리턴합니다.")
    @ApiImplicitParam(name = "id", value = "회원 기본키", example = "1", required = true)
    public ResponseEntity<UserResponseDto> getProfile(@RequestParam Long id) {
        return ResponseEntity.ok().body(userService.getProfile(id));
    }

    @PutMapping("/api/user/setProfile")
    @ApiOperation(value = "회원 정보 수정", notes = "회원 정보 수정를 수정한 후, 수정된 회원 정보를 리턴합니다.")
    public ResponseEntity<UserResponseDto> setProfile(@RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        return ResponseEntity.ok().body(userService.setProfile(userUpdateRequestDto));
    }

    @DeleteMapping("/api/user/setWithdraw")
    @ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴를 한 후, true를 리턴합니다.")
    @ApiImplicitParam(name = "id", value = "회원 기본키", example = "1", required = true)
    public ResponseEntity<Boolean> setWithdraw(@RequestParam Long id) {
        return ResponseEntity.ok().body(userService.setWithdraw(id));
    }
}
