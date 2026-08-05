package com.example.muzip.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// 유저 도메인 (users 테이블 매핑)
@Getter
@Setter
public class User {
    private int userId; // 유저 PK
    private String username; // 로그인 아이디
    private String password; // 암호화된 비밀번호
    private String nickname; // 화면에 표시되는 닉네임
    private String role; // 권한 (예: ROLE_USER)
    private LocalDateTime createdAt; // 가입일시
}
