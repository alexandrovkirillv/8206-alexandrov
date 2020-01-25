package ru.focusstart.tomsk.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.focusstart.tomsk.api.dto.StudentDto;
import ru.focusstart.tomsk.service.student.StudentService;

import java.util.List;

@RestController
@RequestMapping(path = Paths.STUDENTS, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public StudentDto create(@RequestBody StudentDto studentDto) {
        return studentService.create(studentDto);
    }

    @GetMapping(path = Paths.ID)
    public StudentDto getById(@PathVariable(name = Parameters.ID) Long id) {
        return studentService.getById(id);
    }

    @GetMapping
    public List<StudentDto> get(
            @RequestParam(name = Parameters.NAME, required = false) String name,
            @RequestParam(name = Parameters.INSTRUCTOR_NAME, required = false) String instructorName
    ) {
        return studentService.get(name, instructorName);
    }

    @PutMapping(path = Paths.ID)
    public StudentDto merge(@PathVariable(name = Parameters.ID) Long id, @RequestBody StudentDto studentDto) {
        return studentService.merge(id, studentDto);
    }

    @DeleteMapping(path = Paths.ID)
    public void delete(@PathVariable(name = Parameters.ID) Long id) {
        studentService.delete(id);
    }
}
