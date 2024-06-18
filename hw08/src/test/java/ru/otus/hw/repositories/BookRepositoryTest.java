package ru.otus.hw.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void testCreateBook() {
        var expected = bookRepository.save(
                new Book(null,
                        "testTitle",
                        new Author("1",
                                "testFullName"),
                        List.of(new Genre("1", "testName1"),
                                new Genre("2", "testName2")))

        );

        var actual = mongoTemplate.findById(expected.getId(), Book.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void testDeleteBookWithComment() {
        var expectedBook = new Book(null,
                "testTitle",
                new Author("1",
                        "testFullName"),
                List.of(new Genre("1", "testName1"),
                        new Genre("2", "testName2")));

        var expected = mongoTemplate.insert(expectedBook.toBuilder().build());

        bookRepository.deleteById(expected.getId());

        assertThat(mongoTemplate.findById(expected.getId(), Book.class)).isNull();
    }
}
