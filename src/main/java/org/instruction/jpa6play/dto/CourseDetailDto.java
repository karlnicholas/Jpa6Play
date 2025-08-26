package org.instruction.jpa6play.dto;

import java.util.List;

public record CourseDetailDto(Long id, String name, InstructorDto instructor, List<StudentDto> students) {
}
