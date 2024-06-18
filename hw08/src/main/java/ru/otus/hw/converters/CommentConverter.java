package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dtos.comment.CommentBookDto;
import ru.otus.hw.dtos.comment.CommentDto;

@Component
@RequiredArgsConstructor
public class CommentConverter {
    public String commentBookToString(CommentBookDto commentBookDto) {
        return "Id: %s, Text: %s, BookId: %s, BookTitle: %s".formatted(
                commentBookDto.id(),
                commentBookDto.text(),
                commentBookDto.bookId(),
                commentBookDto.bookTitle());
    }

    public String commentToString(CommentDto commentDto) {
        return "Id: %s, Text: %s".formatted(
                commentDto.id(),
                commentDto.text());
    }
}
