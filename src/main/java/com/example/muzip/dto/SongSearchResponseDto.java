package com.example.muzip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor // 필드 전부 매개변수로 받는 생성자
// JSON 응답 => 내 프로젝트 필드로 매핑
public class SongSearchResponseDto {
    private String apiSongId; // 외부 API(Deezer) 곡 식별자
    private String title; // 곡 제목
    private String artist; // 가수
    private String albumImg; // 앨범 커버 이미지 URL
    private String preview; // 프리뷰 음원 (30초)
}
