package org.instruction.jpa6play.model;

import jakarta.persistence.*;
import lombok.*;
import org.instruction.jpa6play.dto.CourseDto;
import org.instruction.jpa6play.dto.InstructorDto;
import org.instruction.jpa6play.dto.SchoolDto;
import org.instruction.jpa6play.dto.StudentDto;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "school")
    private Set<Course> courses;
    @OneToMany(mappedBy = "school")
    private Set<Student> students;
    @OneToMany(mappedBy = "school")
    private Set<Instructor> instructors;

    public static SchoolDto toDto(School s) {
        return new SchoolDto(s.getId(), s.getName(),
                s.getInstructors().stream().map(i -> new InstructorDto(i.getId(), i.getName())).toList(),
                s.getCourses().stream().map(c -> new CourseDto(c.getId(), c.getName())).toList(),
                s.getStudents().stream().map(st -> new StudentDto(st.getId(), st.getName())).toList());
    }
}
