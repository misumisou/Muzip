package com.example.muzip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

// 목록 조회 응답을 페이징 정보와 함께 감싸는 공통 DTO
@Getter
@AllArgsConstructor
public class PageResponse<T> {

    private final List<T> content; // 현재 페이지 데이터
    private final int page; // 현재 페이지 번호 (1부터 시작)
    private final int size; // 페이지당 개수
    private final long totalElements; // 전체 개수
    private final int totalPages; // 전체 페이지 수

}
