package com.example.muzip.exception;

import com.example.muzip.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

// 전역 예외 처리기: @RestController에서 발생한 예외만 처리 (JSON 응답)
// @Controller(화면) 쪽 예외는 여기서 처리하지 않고 Spring Boot 기본 /error(templates/error.html)로 넘김
@Slf4j // 로그 객체 생성
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    // 게시글 못 찾음 -> 404
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerPostNotFound(PostNotFoundException e) {
        ErrorResponse response = ErrorResponse.builder()
                                              .timestamp(LocalDateTime.now())
                                              .status(HttpStatus.NOT_FOUND.value())
                                              .message(e.getMessage())
                                              .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // 작성자 본인 아님 -> 403
    @ExceptionHandler(PostAccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handlePostAccessDenied(PostAccessDeniedException e) {
        ErrorResponse response = ErrorResponse.builder()
                                              .timestamp(LocalDateTime.now())
                                              .status(HttpStatus.FORBIDDEN.value())
                                              .message(e.getMessage())
                                              .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    // @Valid 검증 실패 -> 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getFieldErrors().stream()
                               .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                               .toList();

        ErrorResponse response = ErrorResponse.builder()
                                              .timestamp(LocalDateTime.now())
                                              .status(HttpStatus.BAD_REQUEST.value())
                                              .message("입력값 검증에 실패했습니다.")
                                              .errors(errors)
                                              .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // 그 외 예상하지 못한 모든 예외 -> 500
    // 내부 예외 메시지는 로그로만 남기고, 클라이언트에는 노출하지 않음
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("예상하지 못한 서버 오류", e);
        ErrorResponse response = ErrorResponse.builder()
                                              .timestamp(LocalDateTime.now())
                                              .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                              .message("서버 내부 오류가 발생했습니다.")
                                              .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
