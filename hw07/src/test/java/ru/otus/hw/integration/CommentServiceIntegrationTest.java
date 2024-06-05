package ru.otus.hw.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.hw.services.CommentService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CommentServiceIntegrationTest {

    @Autowired
    CommentService commentService;

    @Test
    @DirtiesContext
    void testFindById() {
        var comment1 = commentService.findById(1L);
        var comment2 = commentService.findById(2L);

        assertThat(comment1).isPresent();
        assertThat(comment1.get().text()).isEqualTo("Cool book");
        assertThat(comment2).isNotPresent();
    }

    @Test
    @DirtiesContext
    void testFindByBookId() {
        var comments = commentService.findByBookId(1L);

        assertThat(comments).hasSize(1);
        assertThat(comments).extracting("text")
                .containsExactlyInAnyOrderElementsOf(Collections.singleton("Cool book"));
    }

    @Test
    @DirtiesContext
    void testCreate() {
        var comment = commentService.create("Very cool book", 1L);

        assertThat(comment).isNotNull();

        var createdComment = commentService.findByBookId(1L);

        assertThat(createdComment).hasSize(2);
        assertThat(createdComment).extracting("text")
                .containsExactlyInAnyOrderElementsOf(List.of("Cool book", "Very cool book"));
    }

    @Test
    @DirtiesContext
    void testUpdate() {

        var updatedComment = commentService.update(1L, "Very cool book");

        assertThat(updatedComment).isNotNull();


        var comment = commentService.findById(1L);

        assertThat(comment).isPresent();
        assertThat(comment)
                .matches(Optional::isPresent)
                .matches(commentBookDto ->Objects.equals(commentBookDto.get().text(), "Very cool book"));
    }

}