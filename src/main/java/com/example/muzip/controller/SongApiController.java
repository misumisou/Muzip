package com.example.muzip.controller;


import com.example.muzip.dto.SongSearchResponseDto;
import com.example.muzip.service.SongApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 검색 결과 JSON 반환
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/song")
public class SongApiController {

    private final SongApiService songApiService;

    // 키워드, 카테고리별 검색을 할 수 있게 param 줌
    @GetMapping("/search")
    public List<SongSearchResponseDto> search(@RequestParam String keyword, @RequestParam(defaultValue = "All") String category) {

        List<SongSearchResponseDto> songList = songApiService.searchSongs(keyword, category);
        return ResponseEntity.ok(songList).getBody();
    }


}