package com.example.muzip.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 커스텀 예외, 작성자 본인이 아닌 사용자가 게시글 수정/삭제를 시도할 때 던짐
@ResponseStatus(HttpStatus.FORBIDDEN)
public class PostAccessDeniedException extends RuntimeException{
    public PostAccessDeniedException(){
        super("본인이 작성한 게시글만 수정/삭제할 수 있습니다.");
    }
}
