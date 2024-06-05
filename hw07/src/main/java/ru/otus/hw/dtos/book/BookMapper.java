package ru.otus.hw.dtos.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dtos.author.AuthorMapper;
import ru.otus.hw.dtos.genre.GenreMapper;
import ru.otus.hw.models.Book;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final AuthorMapper authorMapper;

    private final GenreMapper genreMapper;

    public BookDto toDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                authorMapper.toDto(book.getAuthor()),
                book.getGenres()
                        .stream()
                        .map(genreMapper::toDto)
                        .toList()
        );
    }

}
