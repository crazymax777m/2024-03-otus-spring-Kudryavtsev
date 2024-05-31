package ru.otus.hw.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
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
