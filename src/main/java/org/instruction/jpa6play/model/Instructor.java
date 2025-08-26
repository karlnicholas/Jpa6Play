package org.instruction.jpa6play.model;

import jakarta.persistence.*;
import lombok.*;
import org.instruction.jpa6play.dto.CourseDto;
import org.instruction.jpa6play.dto.InstructorDetailDto;
import org.instruction.jpa6play.dto.InstructorDto;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "instructor")
    private Set<Course> courses;
    @ManyToOne(fetch = FetchType.LAZY)
    private School school;

    public static InstructorDto toDto(Instructor instructor) {
        return new InstructorDto(instructor.getId(), instructor.getName());
    }

    public static InstructorDetailDto toDetailDto(Instructor instructor) {
        return new InstructorDetailDto(instructor.getId(), instructor.getName(), instructor.getCourses().stream().map(c -> new CourseDto(c.getId(), c.getName())).toList());
    }
}
