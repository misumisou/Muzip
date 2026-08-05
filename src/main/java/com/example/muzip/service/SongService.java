package com.example.muzip.service;

import com.example.muzip.domain.Song;
import com.example.muzip.mapper.SongMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// 곡 조회 및 보관함(즐겨찾기) 관리 비즈니스 로직
@Service
@RequiredArgsConstructor
public class SongService {

    private final SongMapper songMapper;

    // 보관함 조회
    public List<Song> getFavorites(Integer userId) {
        return songMapper.findFavoritesByUser(userId);
    }

    // 보관함 추가
    public void addSong(Song song, Integer userId) {
        // 1. songs 테이블에 이미 있는 곡인지 확인
        Song existing = songMapper.findByApiSongId(song.getApiSongId());

        Integer songId;
        if(existing != null){
            songId = existing.getSongId();
        } else {
            // 2. 없으면 songs에 먼저 저장
            songMapper.insertSong(song);
            songId = song.getSongId(); // insert 후 useGenerateKey로 채워짐
        }
        // 3. favorites에는 참조만 저장
        songMapper.addFavorite(userId, songId);
    }

    // 보관함 삭제
    public void deleteSong(Integer userId, Integer songId) {
        songMapper.deleteFavorite(userId, songId);
    }

    // 회원 탈퇴 시 보관함 전체 삭제
    public void deleteAllFavorites(Integer userId) {
        songMapper.deleteAllFavoritesByUser(userId);
    }
}
