package ru.focusstart.tomsk.mapper;

import org.springframework.stereotype.Component;
import ru.focusstart.tomsk.api.dto.BookDto;
import ru.focusstart.tomsk.entity.Book;

@Component
public class BookMapper {

    public BookDto toDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .description(book.getDescription())
                .isbn(book.getIsbn())
                .authorId(book.getAuthor().getId())
                .build();
    }
}
