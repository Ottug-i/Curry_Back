package com.ottugi.curry.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER("일반 사용자"),
    MANAGER("관리자");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public static Role ofRole(String role) {
        return Arrays.stream(Role.values())
                .filter(r -> r.getRole().equals(role))
                .findAny().orElse(null);
    }
}