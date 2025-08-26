package org.instruction.jpa6play.api;

import org.instruction.jpa6play.dto.StudentDetailDto;
import org.instruction.jpa6play.dto.StudentDto;
import org.instruction.jpa6play.model.Student;
import org.instruction.jpa6play.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Creates a new Student.
     */
    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@RequestBody Student student) {
        StudentDto createdStudent = Student.toDto(studentService.createStudent(student));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{name}")
                .buildAndExpand(createdStudent.name())
                .toUri();
        return ResponseEntity.created(location).body(createdStudent);
    }

    /**
     * Reads a single Student by name.
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
     */
    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return ResponseEntity.ok(studentService.findAllStudents().stream().map(Student::toDto).toList());
    }

    /**
     * Updates an existing Student.
     */
    @PutMapping("/{name}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable String name, @RequestBody Student student) {
        Optional<Student> existingStudent = studentService.findStudentByName(name);
        StudentDto studentDto;
        if (existingStudent.isPresent()) {
            studentDto = Student.toDto(studentService.updateStudent(name, student));
            return ResponseEntity.ok(studentDto);
        } else {
            studentDto = Student.toDto(studentService.createStudent(student));
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{name}")
                    .buildAndExpand(studentDto.name())
                    .toUri();
            return ResponseEntity.created(location).body(studentDto);
        }
    }

    /**
     * Deletes a Student.
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String name) {
        if (studentService.deleteStudent(name)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}