package ru.focusstart.tomsk.mapper;

import org.springframework.stereotype.Component;
import ru.focusstart.tomsk.api.dto.InstructorDto;
import ru.focusstart.tomsk.entity.Instructor;

@Component
public class InstructorMapper {

    public InstructorDto toDto(Instructor instructor) {
        return InstructorDto.builder()
                .id(instructor.getId())
                .name(instructor.getName())
                .description(instructor.getDescription())
                .build();
    }
}
