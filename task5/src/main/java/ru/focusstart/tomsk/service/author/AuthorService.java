package ru.focusstart.tomsk.service.author;

import ru.focusstart.tomsk.api.dto.AuthorDto;
import ru.focusstart.tomsk.api.dto.BookDto;

import java.util.List;

public interface AuthorService {

    AuthorDto create(AuthorDto authorDto);

    AuthorDto getById(Long id);

    List<AuthorDto> get(String name);

    List<BookDto> getBooks(Long id);

    AuthorDto merge(Long id, AuthorDto authorDto);
}
