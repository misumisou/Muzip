package com.example.muzip.config;

import com.example.muzip.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// 인증된 사용자 객체
@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user; // DB에서 조회한 사용자 DTO

    // 컨트롤러에서 사용할 userId
    public Integer getUserId() {
        return user.getUserId();
    }

    // 사용자 권한 목록 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole()));
    }

    // <4. 비밀번호 검증> DaoAuthenticationProvider가 이 값(DB의 암호화된 비밀번호)을
    // 사용자가 입력한 평문 비밀번호와 BCryptPasswordEncoder로 비교할 때 씀 (SecurityConfig.passwordEncoder() 참고)
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 로그인 시도 시 username 질문
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public String getNickname() {
        return user.getNickname();
    }

    // 로그인 성공 판정 전 추가 체크 (4가지) - 기본 true 처리
    // 계정 만료
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    // 잠금
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    // 패스워드 만료
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // 활성화 여부
    @Override
    public boolean isEnabled() {
        return true;
    }
}
