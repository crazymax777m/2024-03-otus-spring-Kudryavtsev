package ru.otus.hw.dto.author;

import org.springframework.stereotype.Component;
import ru.otus.hw.models.Author;

@Component
public class AuthorMapper {

    public AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getFullName());
    }

}