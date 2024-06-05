package ru.otus.hw.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    BookRepository repository;

    @Test
    public void testFindAll() {
        assertDoesNotThrow(() -> {
            var books = repository.findAll();

            assertThat(books).isNotEmpty();
        });
    }

    @Test
    public void testFindById() {
        for (Long i: LongStream.range(1,4).boxed().toList()) {
            var realBook = repository.findById(i);

            assertThat(realBook).isPresent();
            assertThat(realBook.get().getTitle()).isEqualTo("BookTitle_%d".formatted(i));
        }

        var fakeBook = repository.findById(4L);

        assertThat(fakeBook).isNotPresent();
    }
}
