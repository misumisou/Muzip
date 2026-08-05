package com.example.muzip.mapper;

import com.example.muzip.domain.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// 게시글 관련 쿼리 매퍼 (구현은 resources/mapper/PostMapper.xml)
@Mapper
public interface PostMapper {

    // 검색 + 페이징 목록 조회
    List<Post> findPost(@Param("keyword") String keyword,
                        @Param("offset") int offset,
                        @Param("size") int size);

    // 검색 조건 맞는 전체 개수 (페이징 계산용)
    long countPost(@Param("keyword") String keyword);

    // 단건 조회
    Post findPostById(@Param("postId") Long id);

    // 작성
    int insertPost(Post post);

    // 수정
    int updatePost(Post post);

    // 삭제
    int deletePost(@Param("postId") Long id);

    // 마이페이지: 특정 유저가 쓴 글 목록
    List<Post> findPostsByUserId(@Param("userId") Integer userId);

    // 회원 탈퇴 시 작성 글 전체 삭제
    void deleteAllPostsByUserId(@Param("userId") Integer userId);
}
