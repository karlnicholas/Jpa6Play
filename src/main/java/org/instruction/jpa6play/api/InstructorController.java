package org.instruction.jpa6play.api;

import org.instruction.jpa6play.dto.InstructorDetailDto;
import org.instruction.jpa6play.dto.InstructorDto;
import org.instruction.jpa6play.model.Instructor;
import org.instruction.jpa6play.service.InstructorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/instructor")
public class InstructorController {
    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    /**
     * Creates a new Instructor.
     */
    @PostMapping
    public ResponseEntity<InstructorDto> createInstructor(@RequestBody Instructor instructor) {
        InstructorDto createdInstructor = Instructor.toDto(instructorService.createInstructor(instructor));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{name}")
                .buildAndExpand(createdInstructor.name())
                .toUri();
        return ResponseEntity.created(location).body(createdInstructor);
    }
    /**
     * Reads a single Instructor by name.
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
     */
    @GetMapping
    public ResponseEntity<List<InstructorDto>> getAllInstructors() {
        return ResponseEntity.ok(instructorService.findAllInstructors().stream().map(Instructor::toDto).toList());
    }

    /**
     * Updates an existing Instructor.
     */
    @PutMapping("/{name}")
    public ResponseEntity<InstructorDto> updateInstructor(@PathVariable String name, @RequestBody Instructor instructor) {
        Optional<Instructor> existingInstructor = instructorService.findInstructorByName(name);
        InstructorDto instructorDto;
        if (existingInstructor.isPresent()) {
            instructorDto = Instructor.toDto(instructorService.updateInstructor(name, instructor));
            return ResponseEntity.ok(instructorDto);
        } else {
            instructorDto = Instructor.toDto(instructorService.createInstructor(instructor));
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{name}")
                    .buildAndExpand(instructorDto.name())
                    .toUri();
            return ResponseEntity.created(location).body(instructorDto);
        }
    }

    /**
     * Deletes an Instructor.
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable String name) {
        if (instructorService.deleteInstructor(name)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}