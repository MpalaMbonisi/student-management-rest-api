package com.mbonisimpala.studentmanagement.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
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
    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NonNull
    @Column(name = "middle_name")
    private String middleName;

    @NonNull
    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NonNull
    @Column(name = "birth_date", nullable = false)
    @Past(message = "The birth day must be in the past")// verify the date input
    private LocalDate birthDate;

    @NonNull
    @Column(name = "nationality", nullable = true)
    @NotBlank(message = "The nationality cannot be blank")
    private String nationality;
}
