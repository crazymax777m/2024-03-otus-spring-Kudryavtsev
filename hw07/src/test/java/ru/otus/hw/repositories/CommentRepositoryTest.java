package ru.otus.hw.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    CommentRepository repository;

    @Test
    public void testFindCommentsByBookId() {
        var realComments = repository.findCommentsByBookId(1L);
        var commentText = realComments.get(0).getText();

        assertThat(realComments).hasSize(1);
        assertThat(commentText).isEqualTo("Cool book");


        var fakeComments = repository.findCommentsByBookId(2L);

        assertThat(fakeComments).hasSize(0);
    }
}
