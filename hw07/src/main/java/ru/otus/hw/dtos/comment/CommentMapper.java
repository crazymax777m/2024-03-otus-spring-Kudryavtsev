package ru.otus.hw.dtos.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Comment;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    public CommentBookDto toBookDto(Comment comment) {
        return new CommentBookDto(comment.getId(),
                                  comment.getText(),
                                  comment.getBook().getId(),
                                  comment.getBook().getTitle());
    }

    public CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText());
    }

}
