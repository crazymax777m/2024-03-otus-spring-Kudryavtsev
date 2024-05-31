package ru.otus.hw.dtos.comment;

public record CommentBookDto(long id, String text, long bookId, String bookTitle) {
}
