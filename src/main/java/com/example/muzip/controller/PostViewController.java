package com.example.muzip.controller;

import com.example.muzip.config.CustomUserDetails;
import com.example.muzip.dto.PostResponse;
import com.example.muzip.service.PostService;
import static com.example.muzip.service.PostService.DEFAULT_PAGE_SIZE;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

// 게시글 화면(Thymeleaf 뷰) 렌더링
@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostViewController {

    private final PostService postService;

    // 게시글 상세 페이지
    @GetMapping("/{postId}")
    public String detail(@PathVariable Long postId, Model model,
    @AuthenticationPrincipal CustomUserDetails userDetails){
        PostResponse post = postService.getPost(postId);
        model.addAttribute("post", post);
        model.addAttribute("user", userDetails);
        return "post/detail";
    }

    // 게시글 목록 페이지 (첫 페이지 10건만 조회)
    @GetMapping
    public String list(Model model) {
        model.addAttribute("posts", postService.getPostList(null, 1, DEFAULT_PAGE_SIZE).getContent());
        return "post/list";
    }
}
