package ru.focusstart.tomsk.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.focusstart.tomsk.api.dto.InstructorDto;
import ru.focusstart.tomsk.api.dto.StudentDto;
import ru.focusstart.tomsk.service.instructor.InstructorService;

import java.util.List;

@RestController
@RequestMapping(path = Paths.INSTRUCTORS, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;

    @PostMapping
    public InstructorDto create(@RequestBody InstructorDto instructorDto) {
        return instructorService.create(instructorDto);
    }

    @GetMapping(path = Paths.ID)
    public InstructorDto getById(@PathVariable(name = Parameters.ID) Long id) {
        return instructorService.getById(id);
    }

    @GetMapping
    public List<InstructorDto> get(@RequestParam(name = Parameters.NAME, required = false) String name) {
        return instructorService.get(name);
    }

    @GetMapping(path = Paths.ID + Paths.STUDENTS)
    public List<StudentDto> getStudents(@PathVariable(name = Parameters.ID) Long id) {
        return instructorService.getStudents(id);
    }

    @PutMapping(path = Paths.ID)
    public InstructorDto merge(@PathVariable(name = Parameters.ID) Long id, @RequestBody InstructorDto instructorDto) {
        return instructorService.merge(id, instructorDto);
    }
}
