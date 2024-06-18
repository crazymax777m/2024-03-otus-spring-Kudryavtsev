package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        var author = mongoTemplate.save(new Author("1", "testFullName"));
        List<Genre> genres = new ArrayList<>();
        genres.add(mongoTemplate.save(new Genre("1", "testName1")));
        genres.add(mongoTemplate.save(new Genre("2", "testName2")));
        mongoTemplate.save(new Book("1", "testTitle", author, genres));
    }

    @Test
    void testCreateComment() {
        var expected = new Comment(
                null,
                "testText",
                Book.builder().id("1").build());


        var saved = commentRepository.save(expected);

        var actual = mongoTemplate.findById(saved.getId(), Comment.class);

        assertThat(actual).isNotNull();
        assertThat(actual.getText()).isEqualTo(expected.getText());
        assertThat(actual.getBook().getId()).isEqualTo(expected.getBook().getId());

    }

    @Test
    void testFindCommentsByBookId() {
        commentRepository.deleteAll();

        var book = Book.builder().id("1").build();

        List<Comment> expected = List.of(
                mongoTemplate.insert(
                        new Comment(
                                null,
                                "testText1",
                                book)),
                mongoTemplate.insert(
                        new Comment(
                                null,
                                "testText2",
                                book)
                ));

        List<Comment> actual = commentRepository.findCommentsByBookId(book.getId());

        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(expected.size());
    }

    @Test
    void testDeleteByBookId() {
        commentRepository.deleteAll();

        var book = Book.builder().id("1").build();

        var created = mongoTemplate.insert(
                new Comment(
                        null,
                        "testText1",
                        book)
        );

        commentRepository.deleteByBookId(book.getId());

        assertThat(mongoTemplate.findById(created.getId(), Comment.class)).isNull();
    }
}

