package com.example.userservice.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// DB의 ROLE 컬럼에 GUEST, USER, MANAGER, ADMIN 이런식으로 데이터가 들어감
@Getter
@RequiredArgsConstructor
public enum UsersRole {
    ADMIN("ROLE_ADMIN", "관리자"),
    MANAGER("ROLE_MANAGER", "매니저"),
    USER("ROLE_USER", "일반 사용자"),
    GUEST("ROLE_GUEST", "손님");

    private final String key;
    private final String title;
}
