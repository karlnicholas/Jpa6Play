package org.instruction.jpa6play.api;

import org.instruction.jpa6play.dto.StudentDetailDto;
import org.instruction.jpa6play.dto.StudentDto;
import org.instruction.jpa6play.model.Student;
import org.instruction.jpa6play.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Creates a new Student.
     * Restricted to users with 'ADMIN' or 'STAFF' roles.
     */
    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(Student.toDto(studentService.createStudent(student)));
    }

    /**
     * Reads a single Student by name.
     * Accessible to any authenticated user with 'ADMIN', 'STAFF', or 'USER' roles.
     */
    @GetMapping("/{name}")
    public ResponseEntity<StudentDetailDto> getStudentByName(@PathVariable String name) {
        return studentService.findStudentDetailsByName(name)
                .map(Student::toDetailDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Reads all Students.
     * Accessible to any authenticated user with 'ADMIN', 'STAFF', or 'USER' roles.
     */
    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return ResponseEntity.ok(studentService.findAllStudents().stream().map(Student::toDto).toList());
    }

    /**
     * Updates an existing Student.
     * Restricted to users with 'ADMIN' or 'STAFF' roles.
     */
    @PutMapping("/{name}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable String name, @RequestBody Student student) {
        return studentService.updateStudent(name, student)
                .map(Student::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Deletes a Student.
     * This is a destructive action, so it is restricted to 'ADMIN' users only.
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String name) {
        if (studentService.deleteStudent(name)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}