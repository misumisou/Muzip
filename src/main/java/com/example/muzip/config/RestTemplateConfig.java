package com.example.muzip.config;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

// Spring은 외부 API 호출용 도구 안 만들어 줌 => 직접 Bean으로 등록해야 주입받아 사용 가능
@Configuration
public class RestTemplateConfig {

    // 빈 등록 => @RequireArgsConstructor 주입받아 사용
    // RestTemplateBuilder => 내부 설정, 인코딩 가이드 등 자동 설정 상속 + 옵션 커스텀 가능
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // builder로 타임아웃 설정, ResTemplate 생성
        return builder.connectTimeout(Duration.ofSeconds(5))
                      .readTimeout(Duration.ofSeconds(5))
                      .build();


    }


}
