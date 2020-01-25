package ru.focusstart.tomsk.mapper;

import org.springframework.stereotype.Component;
import ru.focusstart.tomsk.api.dto.StudentDto;
import ru.focusstart.tomsk.entity.Student;

@Component
public class StudentMapper {

    public StudentDto toDto(Student student) {
        return StudentDto.builder()
                .id(student.getId())
                .name(student.getName())
                .description(student.getDescription())
                .isbn(student.getIsbn())
                .instructorId(student.getInstructor().getId())
                .build();
    }
}
