package com.ottugi.curry.web.controller;

import com.ottugi.curry.service.auth.AuthService;
import com.ottugi.curry.web.dto.auth.TokenResponseDto;
import com.ottugi.curry.web.dto.auth.UserSaveRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Auth API (로그인 API)"})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @ApiOperation(value = "소셜 회원가입과 로그인", notes = "소셜 회원가입 또는 로그인을 한 후, 토큰과 사용자 정보를 리턴합니다.")
    public ResponseEntity<TokenResponseDto> singUpOrLogin(@RequestBody @Valid UserSaveRequestDto requestDto,
                                                          HttpServletResponse response) {
        return ResponseEntity.ok().body(authService.signInOrSignUpAndIssueToken(requestDto, response));
    }

    @PostMapping("/reissue")
    @ApiOperation(value = "토큰 재발급", notes = "재발급된 토큰 정보를 리턴합니다.")
    @ApiImplicitParam(name = "email", value = "회원 이메일", example = "wn8925@sookmyung.ac.kr", required = true)
    public ResponseEntity<TokenResponseDto> reissueToken(@RequestParam @Email String email,
                                                         HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok().body(authService.reissueToken(email, request, response));
    }
}
