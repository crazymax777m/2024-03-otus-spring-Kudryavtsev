package ru.otus.hw.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dtos.author.AuthorMapper;
import ru.otus.hw.dtos.book.BookDto;
import ru.otus.hw.dtos.book.BookMapper;
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

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Import({BookServiceImpl.class,
        BookMapper.class,
        AuthorMapper.class,
        GenreMapper.class})
public class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private BookRepository bookRepository;

    private Author author;
    private Genre genre;

    @BeforeEach
    void setUp() {
        author = new Author(1L, "Test Author");
        genre = new Genre(1L, "Test Genre");
    }

    @Test
    @DirtiesContext
    void testFindAllBooks() {
        // given
        Book book = new Book(1L, "Test Book", author, Set.of(genre));
        when(bookRepository.findAll()).thenReturn(List.of(book));

        // when
        List<BookDto> books = bookService.findAll();

        // then
        assertThat(books).hasSize(1);
        assertThat(books.get(0).id()).isEqualTo(1L);
        assertThat(books.get(0).title()).isEqualTo("Test Book");
        assertThat(books.get(0).author().id()).isEqualTo(1L);
        assertThat(books.get(0).author().fullName()).isEqualTo("Test Author");
        assertThat(books.get(0).genres()).hasSize(1);
        assertThat(books.get(0).genres().get(0).id()).isEqualTo(1L);
        assertThat(books.get(0).genres().get(0).name()).isEqualTo("Test Genre");
    }
}
