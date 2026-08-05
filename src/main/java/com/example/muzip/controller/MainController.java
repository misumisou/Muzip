package com.example.muzip.controller;

import com.example.muzip.config.CustomUserDetails;
import com.example.muzip.domain.Song;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import com.example.muzip.service.SongService;
import com.example.muzip.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

// 페이지 렌더링 + 폼 제출 처리
@Controller
@RequiredArgsConstructor
public class MainController {

    private final SongService songService;

    // 메인 페이지 (곡 조회)
    @GetMapping("/")
    public String main(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        model.addAttribute("user", userDetails);
        // 게시글 패널 JS(searchPosts)가 쓸 페이지 크기 -> /post 목록 페이지와 동일한 값으로 통일
        model.addAttribute("postPageSize", PostService.DEFAULT_PAGE_SIZE);
        if(userDetails != null) {
            model.addAttribute("favorites", songService.getFavorites(userDetails.getUserId()));
        }
        return "main";
    }
    
    // 보관함 추가
    @PostMapping("/add")
    public String addSong(Song song, @AuthenticationPrincipal CustomUserDetails userDetails) {
        songService.addSong(song, userDetails.getUserId());
        return "redirect:/"; // 저장 후 메인 페이지로 리다이렉트
    }

    // 보관함 삭제
    @PostMapping("/delete/{songId}")
    public String deleteSong(@PathVariable Integer songId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        songService.deleteSong(userDetails.getUserId(), songId);
        return "redirect:/"; // 저장 후 메인 페이지로 리다이렉트
    }
}
