package org.instruction.jpa6play.service;

import org.instruction.jpa6play.model.School;
import org.instruction.jpa6play.repository.SchoolRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SchoolService {
    private final SchoolRepository schoolRepository;

    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public Optional<School> findByNameFetchInstructorsFetchCoursesFetchStudends(String schoolName) {
        return schoolRepository.findByNameFetchInstructorsFetchCoursesFetchStudends(schoolName);
    }

    public Optional<School> findByName(String school) {
        return schoolRepository.findByName(school);
    }
}
