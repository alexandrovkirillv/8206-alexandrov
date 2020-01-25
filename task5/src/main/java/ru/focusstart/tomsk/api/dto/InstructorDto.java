package ru.focusstart.tomsk.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "Builder", toBuilder = true)
@JsonDeserialize(builder = InstructorDto.Builder.class)
public class InstructorDto {

    private final Long id;

    private final String name;

    private final String description;


    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
