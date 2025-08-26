package org.instruction.jpa6play.api;

import org.instruction.jpa6play.dto.InstructorDetailDto;
import org.instruction.jpa6play.dto.InstructorDto;
import org.instruction.jpa6play.model.Instructor;
import org.instruction.jpa6play.service.InstructorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructor")
public class InstructorController {
    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    /**
     * Creates a new Instructor.
     * Restricted to users with 'ADMIN' or 'STAFF' roles.
     */
    @PostMapping
    public ResponseEntity<InstructorDto> createInstructor(@RequestBody Instructor instructor) {
        return ResponseEntity.ok(Instructor.toDto(instructorService.createInstructor(instructor)));
    }

    /**
     * Reads a single Instructor by name.
     * Accessible to any authenticated user with 'ADMIN', 'STAFF', or 'USER' roles.
     */
    @GetMapping("/{name}")
    public ResponseEntity<InstructorDetailDto> getInstructorByName(@PathVariable String name) {
        return instructorService.findInstructorDetailsByName(name)
                .map(Instructor::toDetailDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Reads all Instructors.
     * Accessible to any authenticated user with 'ADMIN', 'STAFF', or 'USER' roles.
     */
    @GetMapping
    public ResponseEntity<List<InstructorDto>> getAllInstructors() {
        return ResponseEntity.ok(instructorService.findAllInstructors().stream().map(Instructor::toDto).toList());
    }

    /**
     * Updates an existing Instructor.
     * Restricted to users with 'ADMIN' or 'STAFF' roles.
     */
    @PutMapping("/{name}")
    public ResponseEntity<InstructorDto> updateInstructor(@PathVariable String name, @RequestBody Instructor instructor) {
        return instructorService.updateInstructor(name, instructor)
                .map(Instructor::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Deletes an Instructor.
     * This is a destructive action, so it is restricted to 'ADMIN' users only.
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable String name) {
        if (instructorService.deleteInstructor(name)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}