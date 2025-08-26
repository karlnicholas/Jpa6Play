package org.instruction.jpa6play.repository;

import org.instruction.jpa6play.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    Optional<Instructor> findByName(String name);

    @Query("SELECT i FROM Instructor i LEFT JOIN FETCH i.courses WHERE i.name = :name")
    Optional<Instructor> findByNameJoinCourses(@Param("name") String name);
}
