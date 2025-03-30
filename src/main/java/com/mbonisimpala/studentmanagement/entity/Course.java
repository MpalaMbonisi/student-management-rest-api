package com.mbonisimpala.studentmanagement.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NonNull
    @Column(name = "subject", nullable = false)
    private String subject;
    @NonNull
    @Column(name = "code", nullable = false)
    private String code; // short code to uniquely identify the course
    @NonNull
    @Column(name = "description", nullable = false)
    private String description;
}
