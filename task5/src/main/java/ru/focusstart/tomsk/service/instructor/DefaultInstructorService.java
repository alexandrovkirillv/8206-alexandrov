package ru.focusstart.tomsk.service.instructor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.focusstart.tomsk.api.dto.InstructorDto;
import ru.focusstart.tomsk.api.dto.StudentDto;
import ru.focusstart.tomsk.entity.Instructor;
import ru.focusstart.tomsk.exception.ObjectNotFoundException;
import ru.focusstart.tomsk.mapper.InstructorMapper;
import ru.focusstart.tomsk.mapper.StudentMapper;
import ru.focusstart.tomsk.repository.instructor.InstructorRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.focusstart.tomsk.service.validation.Validator.*;

@Service
@RequiredArgsConstructor
public class DefaultInstructorService implements InstructorService {

    private final InstructorRepository instructorRepository;

    private final InstructorMapper instructorMapper;

    private final StudentMapper studentMapper;

    @Override
    @Transactional
    public InstructorDto create(InstructorDto instructorDto) {
        validate(instructorDto);

        Instructor instructor = add(null, instructorDto);

        return instructorMapper.toDto(instructor);
    }

    @Override
    @Transactional(readOnly = true)
    public InstructorDto getById(Long id) {
        checkNotNull("id", id);

        Instructor instructor = getInstructor(id);

        return instructorMapper.toDto(instructor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InstructorDto> get(String name) {
        return instructorRepository.findByNameContainingIgnoreCase(name == null ? "" : name)
                .stream()
                .map(instructorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> getStudents(Long id) {
        checkNotNull("id", id);

        return Optional.ofNullable(getInstructor(id).getStudents())
                .orElse(Collections.emptyList())
                .stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public InstructorDto merge(Long id, InstructorDto instructorDto) {
        checkNotNull("id", id);
        validate(instructorDto);

        Instructor instructor = instructorRepository.findById(id)
                .map(existing -> update(existing, instructorDto))
                .orElseGet(() -> add(id, instructorDto));

        return instructorMapper.toDto(instructor);
    }

    private void validate(InstructorDto instructorDto) {
        checkNull("instructor.id", instructorDto.getId());
        checkSize("instructor.name", instructorDto.getName(), 1, 256);
        checkSize("instructor.description", instructorDto.getDescription(), 1, 4096);
    }

    private Instructor add(Long id, InstructorDto instructorDto) {
        Instructor instructor = new Instructor();
        instructor.setId(id);
        instructor.setName(instructorDto.getName());
        instructor.setDescription(instructorDto.getDescription());

        return instructorRepository.save(instructor);
    }

    private Instructor getInstructor(Long id) {
        return instructorRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Instructor with id %s not found", id)));
    }

    private Instructor update(Instructor instructor, InstructorDto instructorDto) {
        instructor.setName(instructorDto.getName());
        instructor.setDescription(instructorDto.getDescription());

        return instructor;
    }
}
