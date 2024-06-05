package ru.otus.hw.dtos.comment;

public record CommentBookDto(String id,
                             String text,
                             String bookId,
                             String bookTitle) {
}
