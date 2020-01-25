package ru.focusstart.tomsk.service.instructor;

import ru.focusstart.tomsk.api.dto.InstructorDto;
import ru.focusstart.tomsk.api.dto.StudentDto;

import java.util.List;

public interface InstructorService {

    InstructorDto create(InstructorDto instructorDto);

    InstructorDto getById(Long id);

    List<InstructorDto> get(String name);

    List<StudentDto> getStudents(Long id);

    InstructorDto merge(Long id, InstructorDto instructorDto);
}
