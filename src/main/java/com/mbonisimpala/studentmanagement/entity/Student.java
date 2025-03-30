package com.mbonisimpala.studentmanagement.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "student")
public class Student {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @NonNull
    @Column(name = "middle_name")
    private String middleName;
    @NonNull
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @NonNull
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    @NonNull
    @Column(name = "nationality", nullable = true)
    private String nationality;
}
