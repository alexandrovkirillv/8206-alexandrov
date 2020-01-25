package ru.focusstart.tomsk.repository.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.focusstart.tomsk.entity.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    String GET_QUERY =
            "select b " +
            "from Student b join fetch b.instructor " +
            "where lower(b.name) like lower(concat('%', :name, '%')) " +
            "  and lower(b.instructor.name) like lower(concat('%', :instructorName, '%'))";

    @Query(GET_QUERY)
    List<Student> find(@Param("name") String name, @Param("instructorName") String instructorName);
}
