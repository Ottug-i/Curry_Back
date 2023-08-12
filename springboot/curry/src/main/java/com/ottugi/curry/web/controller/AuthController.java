package com.ottugi.curry.web.controller;

import com.ottugi.curry.service.auth.AuthService;
import com.ottugi.curry.web.dto.auth.TokenResponseDto;
import com.ottugi.curry.web.dto.auth.UserSaveRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags={"Auth API (로그인 API)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ApiOperation(value = "소셜 회원가입과 로그인", notes = "소셜 회원가입 또는 로그인을 한 후, 토큰과 사용자 정보를 리턴합니다.")
    public ResponseEntity<TokenResponseDto> login(@RequestBody UserSaveRequestDto userSaveRequestDto, HttpServletResponse response) {
        return ResponseEntity.ok().body(authService.login(userSaveRequestDto, response));
    }

    @PostMapping("/reissue")
    @ApiOperation(value = "토큰 재발급", notes = "재발급된 토큰 정보를 리턴합니다.")
    @ApiImplicitParam(name = "email", value = "회원 이메일", example = "wn8925@sookmyung.ac.kr", required = true)
    public ResponseEntity<TokenResponseDto> reissueToken(@RequestParam String email, HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok().body(authService.reissueToken(email, request, response));
    }
}
