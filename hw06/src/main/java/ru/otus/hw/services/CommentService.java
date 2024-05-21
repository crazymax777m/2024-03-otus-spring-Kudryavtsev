package ru.otus.hw.services;

import ru.otus.hw.dtos.comment.CommentBookDto;
import ru.otus.hw.dtos.comment.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    CommentBookDto insert(String text, long bookId);

    CommentBookDto update(long id, String text);

    Optional<CommentBookDto> findById(long id);

    List<CommentDto> findByBookId(long bookId);
}
