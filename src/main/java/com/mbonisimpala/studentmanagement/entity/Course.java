package com.mbonisimpala.studentmanagement.entity;

import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Course {
    private Long id;
    @NonNull
    private String subject;
    @NonNull
    private String code; // short code to uniquely identify the course
    @NonNull
    private String description;
}
