package com.ottugi.curry.domain.user;

import com.ottugi.curry.except.BaseCode;
import com.ottugi.curry.except.InvalidException;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("일반 사용자"),
    MANAGER("관리자");

    private final String roleName;

    private static final Map<String, Role> ROLE_MAP = new HashMap<>();

    static {
        for (Role role : Role.values()) {
            ROLE_MAP.put(role.getRoleName(), role);
        }
    }

    public static Role findByRoleName(String role) {
        Role foundRole = ROLE_MAP.get(role);
        if (foundRole == null) {
            throw new InvalidException(BaseCode.BAD_REQUEST);
        }
        return foundRole;
    }
}