package com.mbonisimpala.studentmanagement.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "course")
public class Course {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "subject", nullable = false)
    @NotBlank(message = "Subject cannot be blank")
    private String subject;

    @NonNull
    @Column(name = "code", nullable = false)
    @NotBlank(message = "Course code cannot be blank")
    private String code; // short code to uniquely identify the course

    @NonNull
    @Column(name = "description", nullable = false)
    @NotBlank(message = "Description cannot be blank")
    private String description;
}
