package ru.focusstart.tomsk.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import javafx.scene.NodeBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "Builder", toBuilder = true)
@JsonDeserialize(builder = BookDto.Builder.class)
public class BookDto {

    private final Long id;

    private final String name;

    private final String description;

    private final String isbn;

    private final Long authorId;

    public BookDto(Long id, String name, String description, String isbn, Long authorId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isbn = isbn;
        this.authorId = authorId;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
