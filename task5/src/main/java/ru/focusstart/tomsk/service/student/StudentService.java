package ru.focusstart.tomsk.service.student;

import ru.focusstart.tomsk.api.dto.StudentDto;

import java.util.List;

public interface StudentService {

    StudentDto create(StudentDto studentDto);

    StudentDto getById(Long id);

    List<StudentDto> get(String name, String instructorName);

    StudentDto merge(Long id, StudentDto studentDto);

    void delete(Long id);
}
