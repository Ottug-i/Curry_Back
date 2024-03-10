package com.ottugi.curry.domain.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ottugi.curry.except.InvalidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoleTest {
    @Test
    @DisplayName("올바른 권한 열거형 값 테스트")
    void testFindByValidRoleName() {
        String validRole = Role.USER.getRoleName();

        Role role = Role.findByRoleName(validRole);

        assertNotNull(role);
        assertEquals(Role.USER, role);
    }

    @Test
    @DisplayName("올바르지 않은 권한 열거형 값 테스트")
    void testFindByInvalidRoleName() {
        String invalidRole = "손님";

        assertThatThrownBy(() -> Role.findByRoleName(invalidRole))
                .isInstanceOf(InvalidException.class);
    }
}