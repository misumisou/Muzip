DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS favorites;
DROP TABLE IF EXISTS songs;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    user_id int auto_increment primary key,
    username varchar(30) not null unique,
    password varchar(255) not null, --컬럼 길이 암호화하면 길어짐
    nickname varchar(30) unique default '익명',
    role varchar(20) default 'ROLE_USER',
    created_at timestamp default current_timestamp
);


CREATE TABLE songs (
    song_id int auto_increment primary key,
    title varchar(255) not null,
    artist varchar(255) not null,
    album_img varchar(200),
    api_song_id varchar(30),
    preview varchar(500)
);


CREATE TABLE favorites (
    fav_id int auto_increment primary key,
    user_id int not null,
    song_id int not null,

    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (song_id) REFERENCES songs(song_id)
);


CREATE TABLE posts (
    post_id int auto_increment primary key,
    user_id int not null,
    song_id int, -- 곡 첨부는 선택사항이라 not null 아님
    title varchar(30),
    content varchar(200),
    writer varchar(30),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,

    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (song_id) REFERENCES songs(song_id)
);
