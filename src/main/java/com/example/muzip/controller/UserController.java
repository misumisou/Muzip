package com.example.muzip.controller;

import com.example.muzip.config.CustomUserDetails;
import com.example.muzip.dto.SignUpRequest;
import com.example.muzip.service.PostService;
import com.example.muzip.service.SongService;
import com.example.muzip.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

// 로그인/회원가입/마이페이지 화면 및 제출 처리
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SongService songService;
    private final PostService postService;

    // 로그인 화면
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // 회원가입 화면
    @GetMapping("/signUp")
    public String signUpForm() {
        return "signUp";
    }

    // 회원가입 제출 처리
    @PostMapping("/signUp")
    public String signup(@Valid @ModelAttribute SignUpRequest request, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", bindingResult.getFieldError().getDefaultMessage());
            return "signUp";
        }

        try {
            userService.registerUser(request);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "signUp";
        }

        return "redirect:/login"; // 가입 후 로그인 페이지로 이동
    }

    // 마이페이지 화면
    @GetMapping("/mypage")
    public String myPage(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        LocalDateTime createdAt = userDetails.getUser().getCreatedAt();
        // DB에 created_at이 없는(과거 계정) 경우 null일 수 있어 방어
        Long daysSinceJoin = (createdAt != null) ? ChronoUnit.DAYS.between(createdAt, LocalDateTime.now()) : null;

        model.addAttribute("nickname", userDetails.getNickname());
        model.addAttribute("daysSinceJoin", daysSinceJoin);
        model.addAttribute("favorites", songService.getFavorites(userDetails.getUserId()));
        model.addAttribute("myPosts", postService.getMyPosts(userDetails.getUserId()));
        return "mypage";
    }

    // 닉네임 수정 처리
    @PostMapping("mypage/nickname")
    public String updateNickname(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  @ModelAttribute("nickname") String nickname) {
        userService.updateNickname(userDetails.getUserId(), nickname);
        // userDetails.getUser()는 세션에 저장된 실제 객체 참조라서, 값만 바꿔주면
        // 재로그인 없이도 다음 화면부터 바로 새 닉네임이 보임
        userDetails.getUser().setNickname(nickname);
        return "redirect:/mypage";
    }

    // 회원 탈퇴 처리
    @PostMapping("/mypage/withdraw")
    public String withdraw(@AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request) {
        userService.withdraw(userDetails.getUserId());
        // 탈퇴 직후 세션/인증 정보 무효화 (재로그인 방지)
        request.getSession().invalidate();
        SecurityContextHolder.clearContext();
        return "redirect:/login";
    }
}
