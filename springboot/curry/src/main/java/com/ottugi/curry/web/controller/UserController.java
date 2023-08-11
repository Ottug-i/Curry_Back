package com.ottugi.curry.web.controller;

import com.ottugi.curry.service.user.UserService;
import com.ottugi.curry.web.dto.user.UserResponseDto;
import com.ottugi.curry.web.dto.user.UserUpdateRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags={"User API (회원 API)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    @ApiOperation(value = "회원 정보 조회", notes = "회원 정보를 리턴합니다.")
    @ApiImplicitParam(name = "id", value = "회원 기본키", example = "1", required = true)
    public ResponseEntity<UserResponseDto> getProfile(@RequestParam Long id) {
        return ResponseEntity.ok().body(userService.getProfile(id));
    }

    @PutMapping("/profile")
    @ApiOperation(value = "회원 정보 수정", notes = "회원 정보 수정를 수정한 후, 수정된 회원 정보를 리턴합니다.")
    public ResponseEntity<UserResponseDto> updateProfile(@RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        return ResponseEntity.ok().body(userService.updateProfile(userUpdateRequestDto));
    }

    @DeleteMapping("/withdraw")
    @ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴를 한 후, true를 리턴합니다.")
    @ApiImplicitParam(name = "id", value = "회원 기본키", example = "1", required = true)
    public ResponseEntity<Boolean> setWithdraw(@RequestParam Long id) {
        return ResponseEntity.ok().body(userService.setWithdraw(id));
    }
}
