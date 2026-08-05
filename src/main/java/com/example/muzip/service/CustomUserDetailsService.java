package com.example.muzip.service;

import com.example.muzip.config.CustomUserDetails;
import com.example.muzip.domain.User;
import com.example.muzip.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Spring Security 로그인 시 사용자 정보를 DB에서 조회해 UserDetails로 변환
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

    private final UserMapper userMapper;

    // <3. DaoAuthenticationProvider 작동> 로그인 시도 시 DaoAuthenticationProvider가 이 메서드를 자동으로 호출해서
    // DB에서 회원 정보를 가져옴 (우리가 직접 호출하는 게 아니라 Spring Security가 UserDetailsService 구현체를 찾아 호출)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        {
            // findByUsername 호출
            User user = userMapper.findUserByUsername(username);

            // null이면 예외 던짐
            if(user == null){throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
            }

            // Security가 관리할 수 있는 UserDetails 객체로 반환 => 결과 보고 로그인 성공/실패 결정
            return new CustomUserDetails(user);
        }
    }

}
