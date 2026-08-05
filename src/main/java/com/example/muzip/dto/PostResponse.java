package com.example.muzip.dto;

import com.example.muzip.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

// 데이터를 주고받을 때 사용
@Getter
@Builder
public class PostResponse {

    private long postId; // 게시글 PK
    private Integer userId; // 작성자 유저 PK
    private String title; // 제목
    private String content; // 내용
    private String writer; // 작성자 닉네임
    private LocalDateTime createdAt; // 작성일시
    private LocalDateTime updatedAt; // 수정일시

    // Post 도메인 -> PostResponse 변환
    public static PostResponse of(Post post) {
        return PostResponse.builder()
                           .postId(post.getPostId())
                           .title(post.getTitle())
                           .content(post.getContent())
                           .writer(post.getWriter())
                           .createdAt(post.getCreatedAt())
                           .updatedAt(post.getUpdatedAt())
                           .userId(post.getUserId())
                           .build();
    }

}
