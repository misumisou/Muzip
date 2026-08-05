package com.example.muzip.service;

import com.example.muzip.dto.ApiSearchResponse;
import com.example.muzip.dto.SongSearchResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

// 검색어 → 호출용 URL 조립 → 실제 호출 → 원본 결과를 우리 형식으로 변환해서 반환
@Service
@RequiredArgsConstructor
public class SongApiService {

    // Http 요청 실제로 날리기
    private final RestTemplate restTemplate;


    // Deezer 검색 API 기본 주소
    private static final String API_SEARCH_URL = "https://api.deezer.com/search";

    // 곡 검색 메서드 (제목, 카테고리 받음)
    public List<SongSearchResponseDto> searchSongs(String keyword, String category) {
        // UriComponentsBuilder: 검색어 인코딩 안 되는 문자 자동 처리
        String url = String.valueOf(UriComponentsBuilder.fromUriString(API_SEARCH_URL)
                                                        .queryParam("q", keyword)
                                                        .encode()
                                                        .build()
                                                        .toUriString());

        System.out.println("========== DEEZER 요청 URL: " + url + " ==========");

        // url로 응답 객체 받아오기
        ApiSearchResponse response = restTemplate.getForObject(url, ApiSearchResponse.class);

        // 결과 담을 빈 리스트
        List<SongSearchResponseDto> songList = new ArrayList<>();

        // 검색 결과 없음 + 응답 이상함 => 빈 리스트 반환
        if (response == null || response.getData() == null)
            return songList;

        // 검색어 대소문자 처리
        String lowerKeyword = keyword.toLowerCase();

        // 원본 리스트 순회 => SongSearchResponseDto로 변환
        for (ApiSearchResponse.Track track : response.getData()) {
            // 곡 제목
            String title = track.getTitle();
            // 가수
            String artist = (track.getArtist() != null) ? track.getArtist().getName() : "";
            // 앨범아트url
            String coverUrl = (track.getAlbum() != null) ? track.getAlbum().getCover_medium() : "";
            // 음원 프리뷰(30초)
            String preview = track.getPreview();

            // 카테고리 Artist인데 검색어에 없으면 제외
            if ("ARTIST".equalsIgnoreCase(category) && !artist.toLowerCase().contains(lowerKeyword)) {
                continue;
            }
            // 카테고리 Title인데 검색어에 없으면 제외
            if ("TITLE".equalsIgnoreCase(category) && !title.toLowerCase().contains(lowerKeyword)) {
                continue;
            }

            // 필터 통과한 track => DTO 담음
            songList.add(new SongSearchResponseDto(track.getId(), title, artist, coverUrl, preview));
        }


        return songList;
    }
}