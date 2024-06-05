package ru.otus.hw.services;

import ru.otus.hw.dtos.comment.CommentBookDto;
import ru.otus.hw.dtos.comment.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    CommentBookDto create(String text, String bookId);

    CommentBookDto update(String id, String text);

    Optional<CommentBookDto> findById(String id);

    List<CommentDto> findByBookId(String bookId);

    void deleteById(String id);
}
