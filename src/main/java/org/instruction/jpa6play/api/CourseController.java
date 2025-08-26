package org.instruction.jpa6play.api;

import org.instruction.jpa6play.dto.CourseDetailDto;
import org.instruction.jpa6play.dto.CourseDto;
import org.instruction.jpa6play.model.Course;
import org.instruction.jpa6play.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // Create a new Course
    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@RequestBody Course course) {
        CourseDto createdCourse = Course.toDto(courseService.createCourse(course));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{name}")
                .buildAndExpand(createdCourse.name())
                .toUri();
        return ResponseEntity.created(location).body(createdCourse);
    }

    // Read a Course by name
    @GetMapping("/{name}")
    public ResponseEntity<CourseDetailDto> getCourseByName(@PathVariable String name) {
        return courseService.findCourseDetailsByName(name)
                .map(Course::toDetailDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Read all Courses
    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        return ResponseEntity.ok(courseService.findAllCourses().stream().map(Course::toDto).toList());
    }

    // Update a Course
    @PutMapping("/{name}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable String name, @RequestBody Course course) {
        Optional<Course> existingCourse = courseService.findCourseByName(name);
        CourseDto courseDto;
        if (existingCourse.isPresent()) {
            courseDto = Course.toDto(courseService.updateCourse(name, course));
            return ResponseEntity.ok(courseDto);
        } else {
            courseDto = Course.toDto(courseService.createCourse(course));
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{name}")
                    .buildAndExpand(courseDto.name())
                    .toUri();
            return ResponseEntity.created(location).body(courseDto);
        }
    }

    // Delete a Course
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String name) {
        if (courseService.deleteCourse(name)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Add a Student to a Course
    @PostMapping("/{courseName}/students/{studentName}")
    public ResponseEntity<Void> addStudentToCourse(@PathVariable String courseName, @PathVariable String studentName) {
        courseService.addStudentToCourse(studentName, courseName);
        return ResponseEntity.ok().build();
    }
}