package ru.focusstart.tomsk.service.student;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.focusstart.tomsk.api.dto.StudentDto;
import ru.focusstart.tomsk.entity.Instructor;
import ru.focusstart.tomsk.entity.Student;
import ru.focusstart.tomsk.exception.InvalidParametersException;
import ru.focusstart.tomsk.exception.ObjectNotFoundException;
import ru.focusstart.tomsk.mapper.StudentMapper;
import ru.focusstart.tomsk.repository.instructor.InstructorRepository;
import ru.focusstart.tomsk.repository.student.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.focusstart.tomsk.service.validation.Validator.*;

@Service
@RequiredArgsConstructor
public class DefaultStudentService implements StudentService {

    private final InstructorRepository instructorRepository;

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    @Override
    @Transactional
    public StudentDto create(StudentDto studentDto) {
        validate(studentDto);

        Student student = add(null, studentDto);

        return studentMapper.toDto(student);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDto getById(Long id) {
        checkNotNull("id", id);

        Student student = getStudent(id);

        return studentMapper.toDto(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> get(String name, String instructorName) {
        return studentRepository.find(name == null ? "" : name, instructorName == null ? "" : instructorName)
                .stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StudentDto merge(Long id, StudentDto studentDto) {
        checkNotNull("id", id);
        validate(studentDto);

        Student student = studentRepository.findById(id)
                .map(existing -> update(existing, studentDto))
                .orElseGet(() -> add(id, studentDto));

        return studentMapper.toDto(student);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        checkNotNull("id", id);

        Student student = getStudent(id);

        studentRepository.delete(student);
    }

    private void validate(StudentDto studentDto) {
        checkNull("student.id", studentDto.getId());
        checkSize("student.name", studentDto.getName(), 1, 256);
        checkSize("student.description", studentDto.getDescription(), 1, 4096);
        checkSize("student.isbn", studentDto.getIsbn(), 1, 64);
        checkNotNull("student.instructorId", studentDto.getInstructorId());
    }

    private Student add(Long id, StudentDto studentDto) {
        Instructor instructor = getInstructor(studentDto.getInstructorId());

        Student student = new Student();
        student.setId(id);
        student.setName(studentDto.getName());
        student.setDescription(studentDto.getDescription());
        student.setIsbn(studentDto.getIsbn());
        student.setInstructor(instructor);

        return studentRepository.save(student);
    }

    private Instructor getInstructor(Long id) {
        return instructorRepository.findById(id)
                .orElseThrow(() -> new InvalidParametersException(String.format("Instructor with id %s not found", id)));
    }

    private Student getStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Student with id %s not found", id)));
    }

    private Student update(Student student, StudentDto studentDto) {
        student.setName(studentDto.getName());
        student.setDescription(studentDto.getDescription());
        student.setIsbn(studentDto.getIsbn());
        if (!studentDto.getInstructorId().equals(student.getInstructor().getId())) {
            Instructor newInstructor = getInstructor(studentDto.getInstructorId());
            student.setInstructor(newInstructor);
        }

        return student;
    }
}
