package com.mbonisimpala.studentmanagement.repository;

import com.mbonisimpala.studentmanagement.entity.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {
}
