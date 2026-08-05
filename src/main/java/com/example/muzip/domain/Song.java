package com.example.muzip.domain;

import lombok.Getter;
import lombok.Setter;

// 곡 도메인 (songs 테이블 매핑)
@Getter
@Setter
public class Song {

    private int songId; // 곡 PK
    private String title; // 곡 제목
    private String artist; // 가수
    private String albumImg; // 앨범 커버 이미지 URL
    private String apiSongId; // 외부 API(Deezer) 곡 식별자
    private String preview; // 프리뷰 음원 (30초)
}
