package ru.otus.hw.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.services.BookService;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @Test
    @DirtiesContext
    void testFindAll() {
        var all = bookService.findAll();
        assertThat(all)
                .anyMatch(bookDto -> Objects.equals("BookTitle_1", bookDto.title()));
    }

    @Test
    @DirtiesContext
    void testFindById() {
        var book = bookService.findById(1L);

        assertThat(book)
                .matches(Optional::isPresent)
                .matches(bookDto -> bookDto.get().id() == 1L);
    }

    @Test
    @DirtiesContext
    void testCreate() {
        var book = bookService.create("New Book", 1L, Set.of(1L, 2L));
        var createdBook = bookService.findById(book.id());

        assertThat(book).isNotNull();
        assertThat(createdBook).isPresent();
    }

    @Test
    @DirtiesContext
    void testDeleteById() {
        var book = bookService.findById(1L);

        assertThat(book).isPresent();

        bookService.deleteById(1L);

        var deletedBook = bookService.findById(1L);

        assertThat(deletedBook).isNotPresent();
    }

    @Test
    @DirtiesContext
    void testUpdate() {
        var book = bookService.findById(1L);

        assertThat(book).isPresent();

        var updatedBook = bookService.update(1L, "New Title", 1L, Set.of(1L));

        assertThat(updatedBook).isNotNull();
        assertThat(updatedBook).usingRecursiveComparison().isNotEqualTo(book);
    }
}
