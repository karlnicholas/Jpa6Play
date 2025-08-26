package org.instruction.jpa6play.api;

import org.instruction.jpa6play.dto.SchoolDto;
import org.instruction.jpa6play.model.School;
import org.instruction.jpa6play.service.SchoolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/school")
public class SchoolController {
    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    // Read a School by name
    @GetMapping("/{name}")
    public ResponseEntity<SchoolDto> getSchool(@PathVariable String name) {
        return schoolService.findByNameFetchInstructorsFetchCoursesFetchStudends(name)
                .map(School::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}