package ru.otus.hw.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void testFindAllAuthors() {
        authorRepository.deleteAll();

        var expected = mongoTemplate.insertAll(List.of(
           new Author(null, "testFullName1"),
           new Author(null, "testFullName2")
        ));

        var actual = authorRepository.findAll();

        assertThat(actual).hasSize(2);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void testFindAuthorById() {
        var expected = mongoTemplate.insert(new Author(null, "testFullName"));

        var actual = authorRepository.findById(expected.getId());

        assertThat(actual).isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
