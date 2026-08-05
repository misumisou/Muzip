package com.example.muzip.service;

import com.example.muzip.domain.User;
import com.example.muzip.dto.SignUpRequest;
import com.example.muzip.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 유저 조회 및 회원가입 비즈니스 로직
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final SongService songService;
    private final PostService postService;

    // <회원가입>
    @Transactional
    public void registerUser(SignUpRequest request) {

        // 1. 아이디 중복 체크
        if(userMapper.findUserByUsername(request.getUsername()) != null){
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // 2. 닉네임 중복 체크
        if(userMapper.findUserByNickname(request.getNickname()) != null){
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

        // 3. 비밀번호 암호화 + 기본 권한 부여
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNickname(request.getNickname());
        user.setRole("ROLE_USER");

        // 4. DB 저장
        userMapper.saveUser(user);
    }

    // <회원 탈퇴>
    // FK 제약(favorites.user_id, posts.user_id) 위반을 막기 위해 자식 데이터부터 삭제
    @Transactional
    public void withdraw(Integer userId) {
        songService.deleteAllFavorites(userId);
        postService.deleteAllByUser(userId);
        userMapper.deleteUser(userId);
    }

    // <닉네임 수정>
    public void updateNickname(Integer userId, String nickname) {
        User existing = userMapper.findUserByNickname(nickname);
        if(existing != null && existing.getUserId() != userId){
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
        userMapper.updateNickname(userId, nickname);
    }

}
