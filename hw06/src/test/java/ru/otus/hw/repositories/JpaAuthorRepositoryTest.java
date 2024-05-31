package ru.otus.hw.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaAuthorRepository.class)
public class JpaAuthorRepositoryTest {

    @Autowired
    private JpaAuthorRepository repository;

    @ParameterizedTest
    @MethodSource("getAllAuthors")
    void shouldFindExpectedAuthorById(Author expectedAuthor) {
        //when
        Optional<Author> actualAuthor = repository.findById(expectedAuthor.getId());

        //then
        assertThat(actualAuthor).isPresent()
                .get().usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    void shouldReturnCorrectAuthors() {
        //given
        List<Author> expectedAuthors = getAllAuthors();

        //when
        List<Author> actualAuthors = repository.findAll();

        //then
        assertThat(actualAuthors).usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedAuthors);
    }

    private static List<Author> getAllAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(id, "Author_" + id))
                .toList();
    }

}