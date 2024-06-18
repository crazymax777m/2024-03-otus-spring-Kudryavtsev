--liquibase formatted sql

--changeset crazymax777m:2024-06-18--create_authors
create table authors (
                         id bigserial,
                         full_name varchar(255),
                         primary key (id)
);

--changeset crazymax777m:2024-06-18--create_genres
create table genres (
                        id bigserial,
                        name varchar(255),
                        primary key (id)
);

--changeset crazymax777m:2024-06-18--create_books
create table books (
                       id bigserial,
                       title varchar(255),
                       author_id bigint not null references authors (id),
                       primary key (id)
);

--changeset crazymax777m:2024-06-18--create_books_genres
create table books_genres (
                              book_id bigint references books(id) on delete cascade,
                              genre_id bigint references genres(id) on delete cascade,
                              primary key (book_id, genre_id)
);