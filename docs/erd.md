# Muzip ERD

기준 파일: [`src/main/resources/schema.sql`](../src/main/resources/schema.sql)

```mermaid
erDiagram
    USERS ||--o{ FAVORITES : "찜한다"
    SONGS ||--o{ FAVORITES : "찜해진다"
    USERS ||--o{ POSTS : "작성한다"
    SONGS |o--o{ POSTS : "선택적으로 첨부된다"

    USERS {
        int user_id PK
        varchar username
        varchar password
        varchar nickname
        varchar role
        timestamp created_at
    }
    SONGS {
        int song_id PK
        varchar title
        varchar artist
        varchar album_img
        varchar api_song_id
    }
    FAVORITES {
        int fav_id PK
        int user_id FK
        int song_id FK
        timestamp created_at
    }
    POSTS {
        int post_id PK
        int user_id FK
        int song_id FK
        varchar title
        varchar content
        varchar writer
        timestamp created_at
        timestamp updated_at
    }
```

- `favorites`, `posts`의 `user_id` → `users.user_id`, `song_id` → `songs.song_id` 외래 키
- `posts.song_id`는 글쓰기 시 곡 선택이 필수가 아니라 NULL 허용
- title/content 길이(140자)는 `PostCreateRequest`의 `@Size(max = 140)` 검증과 일치
