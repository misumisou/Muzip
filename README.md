# Muzip 🎧

음악 취향을 공유하는 서비스입니다. 회원가입 후 로그인하면 곡을 검색해 개인 보관함에 담고, 게시판에 글을 쓰고 다른 사용자와 소통할 수 있습니다.

## 📕 주요 기능

- **회원가입 / 로그인** — Spring Security 기반 폼 로그인, 비밀번호는 BCrypt로 암호화하여 저장
- **곡 검색** — [Deezer](https://developers.deezer.com/api) 검색 API를 호출해 제목/가수 기준으로 검색
- **보관함(즐겨찾기)** — 검색한 곡을 개인 보관함에 담고 빼기
- **게시판** — 글 작성/조회/수정/삭제(CRUD), 키워드 검색, 페이징, 작성자 본인만 수정·삭제 가능
- **API 문서 자동화** — springdoc-openapi 기반 Swagger UI
- **글로벌 전역 예외 처리** — 404 / 403 / 400 / 500을 한곳에서 일관된 형식으로 응답

## 🔧 기술 스택

| 구분 | 내용 |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.5.16 |
| Web | Spring Web MVC, Thymeleaf |
| 인증 | Spring Security (BCrypt) |
| DB 연동 | MyBatis 3.0.3 |
| DB | MySQL |
| 검증 | Spring Validation (`@Valid`) |
| API 문서 | springdoc-openapi (Swagger UI) |
| 빌드 | Gradle |
| 기타 | Lombok, spring-dotenv(.env 로딩) |

## 📁 프로젝트 구조

```
src/main/java/com/example/muzip
├── config/      Spring Security, RestTemplate 등 설정
├── controller/  요청을 받아 서비스로 위임 (화면 렌더링 + REST API)
├── domain/      DB 테이블과 매핑되는 VO (User, Post, Song)
├── dto/         요청/응답 전용 객체
├── mapper/      MyBatis 매퍼 인터페이스
├── service/     비즈니스 로직
└── exception/   커스텀 예외 + 전역 예외 처리기

src/main/resources
├── mapper/      MyBatis 쿼리 XML
├── templates/   Thymeleaf 화면
├── schema.sql   DB 스키마 정의 (참고용, 자동 실행 안 됨)
├── application.yaml         공통 설정
└── application-local.yaml   로컬 개발용 DB 접속 설정
```

## 📚 ERD

[docs/erd.md](docs/erd.md) 참고. `users`, `songs`, `favorites`, `posts` 4개 테이블로 구성되어 있습니다.

## 💻 실행 방법

### 1. 사전 준비

- Java 21
- MySQL (로컬에 `muzipdb` 데이터베이스 필요)
- `src/main/resources/schema.sql`을 MySQL에 직접 실행해 테이블 생성

### 2. 환경변수 설정

프로젝트 루트에 `.env` 파일을 만들고 아래 값을 채워주세요.

```env
DB_USERNAME=root
DB_PASSWORD=your_password
```

### 3. 실행

```bash
./gradlew bootRun
```

`http://localhost:8080` 접속 ➡ 로그인/회원가입

### 4. API 문서 확인

서버 실행 후 아래 주소에서 REST API 명세를 볼 수 있습니다.

```
http://localhost:8080/swagger-ui/index.html
```
