package ru.otus.hw.repositories;

import org.bson.BsonDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class GenreRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate.getCollection("genres").deleteMany(new BsonDocument());
    }

    @Test
    void testFindAll() {
        var expected = new ArrayList<>(
                mongoTemplate.insertAll(
                        List.of(
                                new Genre(null, "testName1"),
                                new Genre(null, "testName2")
                        )
                )
        );

        var actual = genreRepository.findAll();

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void testFindAllByIds() {
        var expected = new ArrayList<>(
                mongoTemplate.insertAll(
                        List.of(
                                new Genre(null, "testName1"),
                                new Genre(null, "testName2")
                        )
                )
        );

        var actual = genreRepository.findAllById(
                List.of(
                        expected.get(0).getId(),
                        expected.get(1).getId()
                )
        );

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
