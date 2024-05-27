package ru.otus.hw.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dtos.author.AuthorDto;
import ru.otus.hw.dtos.author.AuthorMapper;
import ru.otus.hw.dtos.book.BookDto;
import ru.otus.hw.dtos.book.BookMapper;
import ru.otus.hw.dtos.genre.GenreDto;
import ru.otus.hw.dtos.genre.GenreMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private BookRepository bookRepository;

    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        var realBookMapper = new BookMapper(new AuthorMapper(), new GenreMapper());
        this.bookService = new BookServiceImpl(
                authorRepository,
                genreRepository,
                bookRepository,
                realBookMapper
        );
    }

    @Test
    void findAll() {
        // given
        var author = new Author(1L, "Test Author");
        var genre = new Genre(1L, "Test Genre");
        var book = new Book(1L, "Test Book", author, Set.of(genre));
        when(bookRepository.findAll()).thenReturn(List.of(book));

        // when
        List<BookDto> books = bookService.findAll();

        // then
        var expected = new BookDto(1L, "Test Book",
                new AuthorDto(1L, "Test Author"),
                List.of(new GenreDto(1L, "Test Genre")));

        assertThat(books)
                .hasSize(1)
                .element(0)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}