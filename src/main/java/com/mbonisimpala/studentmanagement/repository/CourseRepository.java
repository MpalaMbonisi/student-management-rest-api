package com.mbonisimpala.studentmanagement.repository;

import com.mbonisimpala.studentmanagement.entity.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
