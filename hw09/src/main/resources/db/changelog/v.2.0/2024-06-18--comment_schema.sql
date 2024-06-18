--liquibase formatted sql

--changeset crazymax777m:2024-06-18--comment_schema
create table comments(
                         id      bigserial primary key,
                         text    varchar not null,
                         book_id bigint  not null references books (id) on delete cascade
);