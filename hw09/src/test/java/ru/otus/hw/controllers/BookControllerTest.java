package ru.otus.hw.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.author.AuthorDto;
import ru.otus.hw.dto.book.BookDto;
import ru.otus.hw.dto.book.BookSummaryDto;
import ru.otus.hw.dto.genre.GenreDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @Test
    void shouldReturnCorrectBooksPage() throws Exception {
        List<BookDto> books = List.of(new BookDto(1, "Test title",
                new AuthorDto(1, "Test fullName"),
                List.of(new GenreDto(1, "Test name1"), new GenreDto(2, "Test name2"))));

        when(bookService.findAll()).thenReturn(books);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("books"))
                .andExpect(model().attribute("books", books));
    }

    @Test
    void shouldReturnCorrectCreateBookPage() throws Exception {
        List<AuthorDto> authors = List.of(new AuthorDto(1, "Test fullName1"), new AuthorDto(2, "Test fullName2"));
        List<GenreDto> genres = List.of(new GenreDto(1, "Test name1"), new GenreDto(2, "Test name2"));

        when(authorService.findAll()).thenReturn(authors);
        when(genreService.findAll()).thenReturn(genres);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("book-form"))
                .andExpect(model().attribute("authors", authors))
                .andExpect(model().attribute("genres", genres))
                .andExpect(model().attributeExists("book"));
    }

    @Test
    void shouldReturnCorrectEditBookPage() throws Exception {
        List<AuthorDto> authors = List.of(new AuthorDto(1, "Test fullName1"), new AuthorDto(2, "Test fullName2"));
        List<GenreDto> genres = List.of(new GenreDto(1, "Test name1"), new GenreDto(2, "Test name2"));
        BookSummaryDto book = new BookSummaryDto(10L, "Test title", authors.get(0).id(), genres.stream()
                .map(GenreDto::id).collect(Collectors.toSet()));

        when(bookService.findById(10)).thenReturn(book);
        when(authorService.findAll()).thenReturn(authors);
        when(genreService.findAll()).thenReturn(genres);

        mockMvc.perform(get("/books/10"))
                .andExpect(status().isOk())
                .andExpect(view().name("book-form"))
                .andExpect(model().attribute("authors", authors))
                .andExpect(model().attribute("genres", genres))
                .andExpect(model().attributeExists("book"));
    }

    @Test
    void shouldReturnNotFoundStatusWhenEditingPageAndIfBookNotFound() throws Exception {
        when(bookService.findById(anyLong())).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/books/10")).andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateBook() throws Exception {
        when(bookService.create(any())).thenReturn(null);

        mockMvc.perform(post("/books")
                        .param("title", "Test title")
                        .param("authorId", "1")
                        .param("genreIds", "1,2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void shouldBadRequestWhenCreateInvalidBook() throws Exception {
        when(bookService.create(any())).thenReturn(null);

        mockMvc.perform(post("/books")
                        .param("title", "  ")
                        .param("authorId", "")
                        .param("genreIds", "1,2"))
                .andExpect(model().hasErrors())
                .andExpect(status().isOk());
    }

    @Test
    void shouldBadRequestWhenUpdateInvalidBook() throws Exception {
        when(bookService.update(any())).thenReturn(null);

        mockMvc.perform(post("/books/1")
                        .param("id", "1")
                        .param("title", "Test title")
                        .param("authorId", "1")
                        .param("genreIds", ""))
                .andExpect(model().hasErrors())
                .andExpect(status().isOk());
    }

    @Test
    void shouldEditBook() throws Exception {
        List<AuthorDto> authors = List.of(new AuthorDto(1, "Test fullName1"), new AuthorDto(2, "Test fullName2"));
        List<GenreDto> genres = List.of(new GenreDto(1, "Test name1"), new GenreDto(2, "Test name2"));
        BookSummaryDto book = new BookSummaryDto(10L, "Test title", 1L, genres.stream()
                .map(GenreDto::id).collect(Collectors.toSet()));

        when(authorService.findAll()).thenReturn(authors);
        when(genreService.findAll()).thenReturn(genres);
        when(bookService.findById(10)).thenReturn(book);
        when(bookService.update(any())).thenReturn(null);


        mockMvc.perform(post("/books/10")
                        .param("title", "Test title")
                        .param("authorId", "2")
                        .param("genreIds", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/books/10"));
    }

    @Test
    void shouldDeleteBook() throws Exception {
        doNothing().when(bookService).deleteById(11);

        mockMvc.perform(post("/books/11/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

}