package ru.focusstart.tomsk.service.book;

import ru.focusstart.tomsk.api.dto.BookDto;

import java.util.List;

public interface BookService {

    BookDto create(BookDto bookDto);

    BookDto getById(Long id);

    List<BookDto> get(String name, String authorName);

    BookDto merge(Long id, BookDto bookDto);

    void delete(Long id);
}
