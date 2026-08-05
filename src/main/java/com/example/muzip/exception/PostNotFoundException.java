package com.example.muzip.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 커스텀 예외, 게시글 찾을 수 없을 때 던짐
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(Long id){
        super("게시글을 찾을 수 없습니다. " + id);
    }
}
