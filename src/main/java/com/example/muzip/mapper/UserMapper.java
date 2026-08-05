package com.example.muzip.mapper;

import com.example.muzip.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// 유저 관련 쿼리 매퍼 (구현은 resources/mapper/UserMapper.xml)
@Mapper
public interface UserMapper {

    // 유저 조회
    User findUserByUsername(String username);

    // 닉네임으로 유저 조회
    User findUserByNickname(String nickname);

    // 유저 저장
    void saveUser(User user);

    // 회원 탈퇴
    void deleteUser(Integer userId);

    // 유저 닉네임 수정
    void updateNickname(Integer userId, String nickname);
}
