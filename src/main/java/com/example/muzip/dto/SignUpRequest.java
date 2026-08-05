package com.example.muzip.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 회원가입 요청 DTO
@Getter
@Setter
@NoArgsConstructor
public class SignUpRequest {

    // 로그인 아이디
    @NotBlank(message = "아이디를 입력해 주세요.")
    @Size(min = 4, max = 20, message = "아이디는 4~20자로 입력해 주세요.")
    private String username;

    // 비밀번호 (평문 입력, 서비스에서 암호화 후 저장)
    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Size(min = 8, max = 50, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;

    // 화면에 표시될 닉네임
    @NotBlank(message = "닉네임을 입력해 주세요.")
    @Size(max = 20, message = "닉네임은 20자를 넘을 수 없습니다.")
    private String nickname;
}
