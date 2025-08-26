package org.instruction.jpa6play.service;

import org.instruction.jpa6play.model.Instructor;
import org.instruction.jpa6play.repository.InstructorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {
    private final InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    // Create a new Instructor
    public Instructor createInstructor(Instructor instructor) {
        if (instructor.getSchool() == null) {
            throw new IllegalArgumentException("School must be set");
        }
        return instructorRepository.save(instructor);
    }

    // Find Instructor by name
    public Optional<Instructor> findInstructorDetailsByName(String name) {
        return instructorRepository.findByNameJoinCourses(name);
    }

    // Find all Instructors
    public List<Instructor> findAllInstructors() {
        return instructorRepository.findAll();
    }

    // Update a Instructor
    public Instructor updateInstructor(String name, Instructor updatedInstructor) {
        return instructorRepository.findByName(name)
                .map(existingInstructor -> {
                    existingInstructor.setName(updatedInstructor.getName());
                    if (updatedInstructor.getSchool() != null) {
                        existingInstructor.setSchool(updatedInstructor.getSchool());
                    }
                    return instructorRepository.save(existingInstructor);
                }).orElseThrow();
    }

    // Delete a Instructor
    public boolean deleteInstructor(String name) {
        return instructorRepository.findByName(name)
                .map(instructor -> {
                    instructorRepository.delete(instructor);
                    return true;
                })
                .orElse(false);
    }

    public Optional<Instructor> findInstructorByName(String name) {
        return instructorRepository.findByName(name);
    }
}
