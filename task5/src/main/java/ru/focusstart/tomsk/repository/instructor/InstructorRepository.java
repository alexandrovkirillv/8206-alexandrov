package ru.focusstart.tomsk.repository.instructor;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.focusstart.tomsk.entity.Instructor;

import java.util.List;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    List<Instructor> findByNameContainingIgnoreCase(String name);
}
