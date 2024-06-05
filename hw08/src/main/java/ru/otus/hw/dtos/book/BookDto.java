package ru.otus.hw.dtos.book;

import ru.otus.hw.dtos.author.AuthorDto;
import ru.otus.hw.dtos.genre.GenreDto;

import java.util.List;

public record BookDto(String id,
                      String title,
                      AuthorDto author,
                      List<GenreDto> genres) {
}
