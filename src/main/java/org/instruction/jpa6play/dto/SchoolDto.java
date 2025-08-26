package org.instruction.jpa6play.dto;

import java.util.List;

public record SchoolDto(Long id, String name, List<InstructorDto> instructors, List<CourseDto> courses,
                        List<StudentDto> students) {
}
