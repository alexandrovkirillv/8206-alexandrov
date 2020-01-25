package ru.focusstart.tomsk.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "Builder", toBuilder = true)
@JsonDeserialize(builder = StudentDto.Builder.class)
public class StudentDto {

    private final Long id;

    private final String name;

    private final String description;

    private final String isbn;

    private final Long instructorId;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
