package com.example.muzip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Spring Security 설정: 인증/인가 규칙과 로그인/로그아웃 방식 정의
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 비밀번호 암호화 빈 등록
    // <4. 비밀번호 검증> DaoAuthenticationProvider가 이 빈을 자동으로 찾아서
    // 로그인 시 입력된 비밀번호와 CustomUserDetails.getPassword()(DB 값)를 비교할 때 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        }

    @Bean
    // <인가 규칙>
    // 들어오는 모든 HTTP 요청 가로잼 -> 필터 체인 정의
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //
                .csrf(csrf -> csrf.ignoringRequestMatchers("/signUp", "/login", "/add", "/delete/**", "/api/post/**"))

                // <URL별 접근 권한 설정>
                // (시작하는 모든 경로는 로그인 없이 접근 허용)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/", "/signUp", "/login", "/css/**", "/js/**", "/images/**")
                       .permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/post", "/api/post/**", "/api/song/**")
                        .permitAll()
                        // Swagger UI / OpenAPI 문서는 로그인 없이 접근 허용
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**")
                        .permitAll()
                       // 그 외 요청 로그인된 사용자만 접근 가능
                       .anyRequest()
                       .authenticated()
                )
                // <로그인 방식>
                // <1. 로그인 요청 ~ 2. AuthenticationManager 호출>
                // POST /login으로 username/password가 오면 Spring Security의 인증 필터가 가로채서
                // AuthenticationManager -> DaoAuthenticationProvider로 인증을 위임함 (우리가 직접 호출하는 코드는 없음)
                .formLogin(form -> form
                        // 로그인 페이지 URL
                        .loginPage("/login")
                        // <5. 인증 완료> 여기 도달했다는 건 인증 성공 -> SecurityContextHolder에 저장 + 세션(JSESSIONID) 생성 완료된 상태
                        // 로그인 성공 시 "/"로 보냄
                        .defaultSuccessUrl("/", true)
                        // form input: username, password
                        .usernameParameter("username")
                        .passwordParameter("password")
                        // 누구나 접근 가능
                        .permitAll())
                // <로그아웃 방식>
                .logout(logout -> logout
                        // 로그아웃 URL
                        .logoutUrl("/logout")
                        // 로그아웃 성공 시 "/login"로 보냄
                        .logoutSuccessUrl("/login?logout")
                        // <5번의 반대> 로그인 때 만들어진 세션(SPRING_SECURITY_CONTEXT 포함)을 여기서 명시적으로 무효화
                        .invalidateHttpSession(true)
                        // 쿠키 삭제
                        .deleteCookies("JSESSIONID")
                        // 누구나 접근 가능
                        .permitAll());

            return http.build();
    }
}
