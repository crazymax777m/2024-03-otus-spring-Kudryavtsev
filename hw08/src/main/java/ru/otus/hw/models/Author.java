package ru.otus.hw.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@AllArgsConstructor
@Document("authors")
public class Author {
    @Id
    private final String id;

    private final String fullName;
}
