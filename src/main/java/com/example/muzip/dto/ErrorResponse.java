package com.example.muzip.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

// 커스텀 예외
@Getter
@Builder
public class ErrorResponse {

    private final int status; // http 상태 코드
    private final LocalDateTime timestamp; // 예외 발생 시각
    private final String message; // 에러 메시지
    private final List<String> errors; // 에러
}
