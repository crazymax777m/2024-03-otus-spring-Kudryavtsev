package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dtos.comment.CommentDto;
import ru.otus.hw.dtos.comment.CommentMapper;
import ru.otus.hw.dtos.comment.CommentBookDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    private final CommentMapper commentMapper;

    @Transactional
    @Override
    public CommentBookDto create(String text, String bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %s not found".formatted(bookId)));

        Comment comment = new Comment(null, text, book);

        return commentMapper.toBookDto(commentRepository.save(comment));
    }

    @Transactional
    @Override
    public CommentBookDto update(String id, String text) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id %s not found".formatted(id)));
        return commentMapper.toBookDto(commentRepository.save(comment.getComment(text)));
    }

    @Override
    public Optional<CommentBookDto> findById(String id) {
        return commentRepository.findById(id)
                .map(commentMapper::toBookDto);
    }

    @Override
    public List<CommentDto> findByBookId(String bookId) {
        return commentRepository.findCommentsByBookId(bookId).stream()
                .map(commentMapper::toDto)
                .toList();
    }

    @Override
    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }
}