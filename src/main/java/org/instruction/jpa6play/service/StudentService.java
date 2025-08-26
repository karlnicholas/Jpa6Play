package org.instruction.jpa6play.service;

import org.instruction.jpa6play.model.Student;
import org.instruction.jpa6play.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Create a new Student
    public Student createStudent(Student student) {
        if (student.getSchool() == null) {
            throw new IllegalArgumentException("School must be set");
        }
        return studentRepository.save(student);
    }

    // Find Student by name
    public Optional<Student> findStudentDetailsByName(String name) {
        return studentRepository.findByNameJoinCourses(name);
    }

    // Find all Students
    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    // Update a Student
    public Student updateStudent(String name, Student updatedStudent) {
        return studentRepository.findByName(name)
                .map(existingStudent -> {
                    existingStudent.setName(updatedStudent.getName());
                    if (updatedStudent.getSchool() != null) {
                        existingStudent.setSchool(updatedStudent.getSchool());
                    }
                    return studentRepository.save(existingStudent);
                }).orElseThrow();
    }

    // Delete a Student
    public boolean deleteStudent(String name) {
        return studentRepository.findByName(name)
                .map(student -> {
                    studentRepository.delete(student);
                    return true;
                })
                .orElse(false);
    }

    public Optional<Student> findStudentByName(String name) {
        return studentRepository.findByName(name);
    }
}