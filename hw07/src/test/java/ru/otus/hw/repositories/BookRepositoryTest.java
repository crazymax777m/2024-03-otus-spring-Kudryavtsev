package ru.otus.hw.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    BookRepository repository;

    @Test
    public void testFindAll() {
        var books = repository.findAll();

        assertThat(books).hasSize(3);
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
