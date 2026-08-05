package com.example.muzip.service;

import com.example.muzip.domain.Post;
import com.example.muzip.dto.PageResponse;
import com.example.muzip.dto.PostCreateRequest;
import com.example.muzip.dto.PostResponse;
import com.example.muzip.exception.PostAccessDeniedException;
import com.example.muzip.exception.PostNotFoundException;
import com.example.muzip.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 게시글 CRUD 비즈니스 로직
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 기본 읽기 전용, 쓰기 메서드 개별 정의
public class PostService {

    // 목록 조회 시 기본 페이지 크기 (다른 곳에서 게시글 목록을 가져올 때도 이 값을 참조해 통일)
    public static final int DEFAULT_PAGE_SIZE = 10;

    private final PostMapper postMapper;

    // 목록 조회
    public PageResponse<PostResponse> getPostList(String keyword, int page, int size) {
        if (page < 1) {
            page = 1;
        }
        int offset = (page - 1) * size;

        List<Post> posts = postMapper.findPost(keyword, offset, size);
        long totalElements = postMapper.countPost(keyword);

        List<PostResponse> content = posts.stream()
                .map(PostResponse::of)
                .toList();

        return new PageResponse<>(content, page, size, totalElements, (int) Math.ceil(totalElements / (double) size));
    }

    // 단건 조회
    @Transactional
    public PostResponse getPost(Long id) {
        Post post = postMapper.findPostById(id);
        if(post == null) {
            throw new PostNotFoundException(id);
        }
        return PostResponse.of(post);
    }

    // 글 작성
    @Transactional
    public PostResponse createPost(PostCreateRequest request, String writer, Integer userId) {
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .writer(writer)
                .userId(userId)
                .songId(request.getSongId())
                .build();

        postMapper.insertPost(post);
        return PostResponse.of(postMapper.findPostById(post.getPostId()));
    }

    // 글 수정
    @Transactional
    public PostResponse updatePost(Long id, PostCreateRequest request, Integer userId) {
        Post post = postMapper.findPostById(id);
        if(post == null) {
            throw new PostNotFoundException(id);
        }
        if (!post.getUserId().equals(userId)){
            throw new PostAccessDeniedException();
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        postMapper.updatePost(post);

        return PostResponse.of(postMapper.findPostById(id));
    }

    // 글 삭제
    @Transactional
    public void deletePost(Long id, Integer userId) {
        Post post = postMapper.findPostById(id);
        if (post == null) {
            throw new PostNotFoundException(id);
        }
        if (!post.getUserId().equals(userId)){
            throw new PostAccessDeniedException();
        }
        postMapper.deletePost(id);
    }

    // 마이페이지: 내가 쓴 글 목록
    public List<PostResponse> getMyPosts(Integer userId) {
        return postMapper.findPostsByUserId(userId).stream()
                .map(PostResponse::of)
                .toList();
    }

    // 회원 탈퇴 시 작성 글 전체 삭제
    @Transactional
    public void deleteAllByUser(Integer userId) {
        postMapper.deleteAllPostsByUserId(userId);
    }
}
