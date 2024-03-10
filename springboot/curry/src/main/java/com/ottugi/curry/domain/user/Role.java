package com.ottugi.curry.domain.user;

import com.ottugi.curry.except.BaseCode;
import com.ottugi.curry.except.InvalidException;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("일반 사용자"),
    MANAGER("관리자");

    private final String role;

    public static Role ofRole(String role) {
        return Arrays.stream(Role.values())
                .filter(r -> r.getRole().equals(role))
                .findAny().orElseThrow(() -> new InvalidException(BaseCode.BAD_REQUEST));

    }
}