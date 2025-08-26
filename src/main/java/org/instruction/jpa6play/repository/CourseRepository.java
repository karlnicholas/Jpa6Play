package org.instruction.jpa6play.repository;

import org.instruction.jpa6play.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByName(String name);

    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.students WHERE c.name = :name")
    Optional<Course> findByNameFetchStudents(String name);

    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.instructor JOIN FETCH c.students WHERE c.name = :name")
    Optional<Course> findByNameFetchInstructorFetchStudents(String name);
}
