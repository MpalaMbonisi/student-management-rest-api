package com.mbonisimpala.studentmanagement.entity;

import com.mbonisimpala.studentmanagement.validation.ValidGrade;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "grade", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "course_id"})
})
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ValidGrade // custom annotation from grade validation
    @Column(name = "grade", nullable = false)
    private String grade;

    // joining the course table
    @ManyToOne // many grades will be linked to one course
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    // joining the student table
    @ManyToOne // many grades will be linked to one student
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;
}
