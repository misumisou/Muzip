package com.example.muzip.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
// Spotify가 주는 JSON 구조 받아서 자동 객체 변환
public class ApiSearchResponse {

    private List<Track> data; // 검색된 트랙 목록

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true) // 안 쓰는 필드 매핑 안 하고 무시
    // 트랙 (아이디, 제목, 가수, 앨범)
    public static class Track {
        private String id; // 외부 API 곡 식별자
        private String title; // 곡 제목
        private Artist artist; // 가수 정보
        private Album album; // 앨범 정보
        private String preview; // 음원 재생(30초)
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    // 가수 객체
    public static class Artist {
        private String name; // 가수 이름
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    // 앨범 객체
    public static class Album {
        private String cover_medium; // 앨범 커버 이미지 URL (중간 크기)
    }


}
