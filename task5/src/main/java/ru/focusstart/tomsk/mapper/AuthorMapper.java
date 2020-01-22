package ru.focusstart.tomsk.mapper;

import org.springframework.stereotype.Component;
import ru.focusstart.tomsk.api.dto.AuthorDto;
import ru.focusstart.tomsk.entity.Author;

@Component
public class AuthorMapper {

    public AuthorDto toDto(Author author) {
        return AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .description(author.getDescription())
                .build();
    }
}
