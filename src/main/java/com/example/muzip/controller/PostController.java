package com.example.muzip.controller;

import com.example.muzip.config.CustomUserDetails;
import com.example.muzip.dto.PageResponse;
import com.example.muzip.dto.PostCreateRequest;
import com.example.muzip.dto.PostResponse;
import com.example.muzip.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

// 게시글 CRUD REST API
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 목록 조회
    @GetMapping
    public ResponseEntity<PageResponse<PostResponse>> getPostList(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) { // PostService.DEFAULT_PAGE_SIZE와 동일한 값으로 유지
        return ResponseEntity.ok(postService.getPostList(keyword, page, size));
    }

    // 게시글 단건 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    // 게시글 등록
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostCreateRequest request,
           @AuthenticationPrincipal CustomUserDetails userDetails) {
        PostResponse response = postService.createPost(request, userDetails.getNickname(), userDetails.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 게시글 수정
    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long postId,
            @Valid @RequestBody PostCreateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(postService.updatePost(postId, request, userDetails.getUserId()));
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.deletePost(postId, userDetails.getUserId());
        return ResponseEntity.noContent().build(); // 204
    }
}
