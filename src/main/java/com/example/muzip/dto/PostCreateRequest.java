package com.example.muzip.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 게시글 등록/작성용 DTO
@Getter
@NoArgsConstructor
public class PostCreateRequest {

    // 게시글 제목
    @NotBlank(message = "제목을 작성해 주세요.")
    @Size(max = 30, message = "제목은 30자를 넘을 수 없습니다.")
    private String title;

    // 게시글 내용
    @NotBlank(message = "내용을 작성해 주세요.")
    @Size(max = 140, message = "내용은 140자를 넘을 수 없습니다.")
    private String content;

    private Integer songId; // 함께 첨부할 곡 PK (선택)

}
