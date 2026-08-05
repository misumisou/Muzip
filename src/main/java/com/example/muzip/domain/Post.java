package com.example.muzip.domain;

import lombok.*;

import java.time.LocalDateTime;

// 게시글 도메인 (posts 테이블 매핑)
@Data // 자바 빈즈 필수 메서드 한 번에 생성
@Builder // 빌더 패턴 사용, 빌더 클래스 자동 생성
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 전체 생성자 자동 생성
@Getter
@Setter
public class Post {

    private long postId; // 게시글 PK
    private String title; // 제목
    private String content; // 내용
    private String writer; // 작성자 닉네임
    private LocalDateTime createdAt; // 작성일시
    private LocalDateTime updatedAt; // 수정일시
    private Integer userId; // 작성자 유저 PK
    private Integer songId; // 함께 첨부한 곡 PK (선택)
}
