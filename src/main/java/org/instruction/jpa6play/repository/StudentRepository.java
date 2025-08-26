package org.instruction.jpa6play.repository;

import org.instruction.jpa6play.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findStudentByName(String studentName);

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.courses  WHERE s.name = :name")
    Optional<Student> findByNameJoinCourses(String name);
}
