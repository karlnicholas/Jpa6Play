package org.instruction.jpa6play.model;

import jakarta.persistence.*;
import lombok.*;
import org.instruction.jpa6play.dto.CourseDetailDto;
import org.instruction.jpa6play.dto.CourseDto;
import org.instruction.jpa6play.dto.InstructorDto;
import org.instruction.jpa6play.dto.StudentDto;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Student> students;
    @ManyToOne(fetch = FetchType.LAZY)
    private Instructor instructor;
    @ManyToOne(fetch = FetchType.LAZY)
    private School school;

    public static CourseDto toDto(Course course) {
        return new CourseDto(course.getId(), course.getName());
    }

    public static CourseDetailDto toDetailDto(Course course) {
        return new CourseDetailDto(course.getId(), course.getName(), new InstructorDto(course.getInstructor().getId(), course.getInstructor().getName()), course.getStudents().stream().map(s -> new StudentDto(s.getId(), s.getName())).toList());
    }
}
