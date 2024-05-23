package ru.otus.hw.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dtos.comment.CommentBookDto;
import ru.otus.hw.dtos.comment.CommentMapper;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Import({CommentServiceImpl.class,
        CommentMapper.class})
public class CommentServiceIntegrationTest {

    @Autowired
    private CommentService commentService;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private BookRepository bookRepository;


    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book(1L, "Test Book", null, null);
    }

    @Test
    @DirtiesContext
    void testFindCommentById() {
        // given
        Comment comment = new Comment(1L, "Test Comment", book);
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        // when
        Optional<CommentBookDto> commentDtoOptional = commentService.findById(1L);

        // then
        assertThat(commentDtoOptional).isPresent();
        CommentBookDto commentDto = commentDtoOptional.get();
        assertThat(commentDto.id()).isEqualTo(1L);
        assertThat(commentDto.text()).isEqualTo("Test Comment");
        assertThat(commentDto.bookId()).isEqualTo(1L);
        assertThat(commentDto.bookTitle()).isEqualTo("Test Book");
    }
}
