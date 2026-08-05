package com.example.muzip.mapper;

import com.example.muzip.domain.Song;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// 곡/보관함 관련 쿼리 매퍼 (구현은 resources/mapper/SongMapper.xml)
@Mapper
public interface SongMapper {

    // 보관함 검증
    Song findByApiSongId(String apiSongId);

    // songs 테이블 새 곡 지정
    void insertSong(Song song);

    // 보관함 추가 (좋아요)
    void addFavorite(@Param("userId") Integer userId, @Param("songId") Integer songId);

    // 보관함 삭제
    void deleteFavorite(@Param("userId") Integer userId, @Param("songId") Integer songId);

    // 특정 유저의 보관함 목록
    List<Song> findFavoritesByUser(Integer userId);

    // 회원 탈퇴 시 보관함 전체 삭제
    void deleteAllFavoritesByUser(Integer userId);
}
