package ru.otus.hw.services;

import ru.otus.hw.dtos.author.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();
}
