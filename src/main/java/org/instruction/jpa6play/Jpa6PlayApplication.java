package org.instruction.jpa6play;

import org.instruction.jpa6play.model.School;
import org.instruction.jpa6play.model.Student;
import org.instruction.jpa6play.service.CourseService;
import org.instruction.jpa6play.service.SchoolService;
import org.instruction.jpa6play.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Jpa6PlayApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Jpa6PlayApplication.class, args);
    }

    @Autowired
    private StudentService studentService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private CourseService courseService;

    @Override
    public void run(String... args) {
        School school = schoolService.findByName("School").orElseThrow();
        Student karlStudent = Student.builder().name("Karl").school(school).build();
        studentService.createStudent(karlStudent);
        courseService.addStudentToCourse("Karl", "Science");
    }
}
