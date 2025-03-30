package com.mbonisimpala.studentmanagement.entity;

import lombok.*;

import java.time.LocalDate;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student {
    private long id;
    @NonNull
    private String firstName;
    private String middleName;
    @NonNull
    private String lastName;
    @NonNull
    private LocalDate birthDate;
    @NonNull
    private String nationality;
}
