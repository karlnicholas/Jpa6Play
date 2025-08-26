package org.instruction.jpa6play.repository;

import org.instruction.jpa6play.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Long> {
    @Query("select s from School s left join fetch s.instructors left join fetch s.courses left join fetch s.students where s.name = :name")
    Optional<School> findByNameFetchInstructorsFetchCoursesFetchStudends(String name);

    Optional<School> findByName(String name);
}
