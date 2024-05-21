package ru.otus.hw.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.*;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaBookRepository.class)
public class JpaBookRepositoryTest {

    @Autowired
    private JpaBookRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    void shouldCreateNewBook() {
        //given
        Book expectedBook = buildBook().build();

        //when
        repository.save(expectedBook);
        em.flush();

        //then
        Book actualBook = em.find(Book.class, expectedBook.getId());
        assertBook(actualBook, expectedBook);
    }

    @Test
    void shouldUpdateBook() {
        //given
        Book book = buildBook().build();
        em.persistAndFlush(book);

        //when
        book.setTitle("updated title");
        book.setAuthor(em.getEntityManager().getReference(Author.class, 2));
        book.setGenres(new HashSet<>(Collections.singletonList(em.getEntityManager().find(Genre.class, 2))));
        Book expectedBook = repository.save(book);
        em.flush();

        //then
        Book actualBook = em.find(Book.class, expectedBook.getId());
        assertBook(actualBook, expectedBook);
    }

    @Test
    void shouldFindBookById() {
        //given
        Book expectedBook = buildBook().title("Interesting book").build();
        em.persistAndFlush(expectedBook);

        //when
        Optional<Book> actualBook = repository.findById(expectedBook.getId());

        //then
        assertThat(actualBook).isPresent();
        assertBook(actualBook.get(), expectedBook);
    }

    @Test
    void shouldReturnCorrectBooks() {
        //given
        List<Book> expectedBooks = getAllBooks();

        //when
        List<Book> actualBooks = repository.findAll();

        //then
        assertThat(actualBooks).hasSize(expectedBooks.size());
    }

    @Test
    void shouldDeleteBookById() {
        //given
        Book expectedBook = buildBook().title("Interesting book").build();
        em.persistAndFlush(expectedBook);

        //when
        repository.deleteById(expectedBook.getId());
        em.flush();

        //then
        assertThat(em.find(Book.class, expectedBook.getId())).isNull();
    }

    @Test
    void shouldDeleteBookWithComment() {
        //given
        Book expectedBook = em.find(Book.class, 1);

        //when
        repository.deleteById(expectedBook.getId());
        em.flush();

        //then
        assertThat(em.find(Book.class, expectedBook.getId())).isNull();
    }

    private static void assertBook(Book actualBook, Book expectedBook) {
        assertThat(actualBook.getId()).isEqualTo(expectedBook.getId());
        assertThat(actualBook.getTitle()).isEqualTo(expectedBook.getTitle());
        assertThat(actualBook.getAuthor().getId()).isEqualTo(expectedBook.getAuthor().getId());
        assertThat(actualBook.getGenres()).hasSize(expectedBook.getGenres().size());
    }

    private Book.BookBuilder buildBook() {
        return Book.builder()
                .id(0)
                .title("Book title")
                .author(em.getEntityManager().getReference(Author.class, 1))
                .genres(new HashSet<>(Arrays.asList(
                        em.getEntityManager().getReference(Genre.class, 1),
                        em.getEntityManager().getReference(Genre.class, 2))));
    }

    private static List<Author> getAllAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(id, "Author_" + id))
                .toList();
    }

    private static List<Genre> getAllGenres() {
        return IntStream.range(1, 7).boxed()
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();
    }

    private static List<Book> getAllBooks(List<Author> dbAuthors, List<Genre> dbGenres) {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Book(id,
                        "BookTitle_" + id,
                        dbAuthors.get(id - 1),
                        new HashSet<>(dbGenres.subList((id - 1) * 2, (id - 1) * 2 + 2))
                ))
                .toList();
    }

    private static List<Book> getAllBooks() {
        var dbAuthors = getAllAuthors();
        var dbGenres = getAllGenres();
        return getAllBooks(dbAuthors, dbGenres);
    }

}