package org.instruction.jpa6play.service;

import jakarta.transaction.Transactional;
import org.instruction.jpa6play.model.Course;
import org.instruction.jpa6play.model.Student;
import org.instruction.jpa6play.repository.CourseRepository;
import org.instruction.jpa6play.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public Optional<Course> findCourseDetailsByName(String name) {
        return courseRepository.findByNameFetchInstructorFetchStudents(name);
    }

    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(String name, Course updatedCourse) {
        return courseRepository.findByName(name)
                .map(existingCourse -> {
                    existingCourse.setName(updatedCourse.getName());
                    if (updatedCourse.getSchool() != null) {
                        existingCourse.setSchool(updatedCourse.getSchool());
                    }
                    if (updatedCourse.getInstructor() != null) {
                        existingCourse.setInstructor(updatedCourse.getInstructor());
                    }
                    return courseRepository.save(existingCourse);
                }).orElseThrow();
    }

    public boolean deleteCourse(String name) {
        return courseRepository.findByName(name)
                .map(course -> {
                    courseRepository.delete(course);
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public void addStudentToCourse(String studentName, String courseName) {
        Student student = studentRepository.findByName(studentName).orElseThrow();
        Course course = courseRepository.findByNameFetchStudents(courseName).orElseThrow();
        course.getStudents().add(student);
    }

    public Optional<Course> findCourseByName(String name) {
        return courseRepository.findByName(name);
    }
}