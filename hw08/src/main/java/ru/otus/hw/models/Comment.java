package ru.otus.hw.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@AllArgsConstructor
@Document("comments")
public class Comment {
    @Id
    private final String id;

    private final String text;

    @DBRef(lazy = true)
    private final Book book;

    public Comment getComment(String text) {
        return new Comment(this.id, text, this.book);
    }

}
