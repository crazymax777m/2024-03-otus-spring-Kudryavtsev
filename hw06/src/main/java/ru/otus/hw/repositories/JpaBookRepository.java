package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Book;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaBookRepository implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Book> findById(long id) {
        var properties = new HashMap<String, Object>();
        properties.put("jakarta.persistence.fetchgraph",
                em.getEntityGraph("book-with-author-graph"));

        return Optional.ofNullable(em.find(Book.class, id, properties));

    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("from Book ",
                        Book.class)
                .setHint("jakarta.persistence.fetchgraph",
                        em.getEntityGraph("book-with-author-graph"))
                .getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public void deleteById(long id) {
        var book = em.find(Book.class, id);

        if (book != null) {
            em.remove(book);
        }
    }
}