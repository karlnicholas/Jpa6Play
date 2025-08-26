package org.instruction.jpa6play.model;

import jakarta.persistence.*;
import lombok.*;
import org.instruction.jpa6play.dto.CourseDto;
import org.instruction.jpa6play.dto.StudentDetailDto;
import org.instruction.jpa6play.dto.StudentDto;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "students")
    private Set<Course> courses;
    @ManyToOne(fetch = FetchType.LAZY)
    private School school;

    public static StudentDto toDto(Student student) {
        return new StudentDto(student.getId(), student.getName());
    }

    public static StudentDetailDto toDetailDto(Student student) {
        return new StudentDetailDto(student.getId(), student.getName(), student.getCourses().stream().map(c -> new CourseDto(c.getId(), c.getName())).toList());
    }
}
