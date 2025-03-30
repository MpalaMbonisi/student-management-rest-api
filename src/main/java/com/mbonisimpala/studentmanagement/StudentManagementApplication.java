package com.mbonisimpala.studentmanagement;

import com.mbonisimpala.studentmanagement.entity.Course;
import com.mbonisimpala.studentmanagement.entity.Student;
import com.mbonisimpala.studentmanagement.repository.CourseRepository;
import com.mbonisimpala.studentmanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class StudentManagementApplication implements CommandLineRunner {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Iterable<Student> students = List.of(new Student[]{
                new Student("Mbonisi", "", "Mpala", LocalDate.parse("2001-12-16"), "Zimbabwe"),
                new Student("Piotr", "Kamil", "Kowalski", LocalDate.parse("2006-05-20"), "Poland"),
                new Student("Ola", "Kasia", "Nowalska", LocalDate.parse("2002-01-13"), "Germany"),
                new Student("Robert","", "Lewandowksi", LocalDate.parse("2004-12-30"), "Poland"),
                new Student("Agata", "Olga", "Duda", LocalDate.parse("2000-01-01"), "Ukraine")
        });
        studentRepository.saveAll(students);

        Iterable<Course> courses = List.of(new Course[] {
                new Course("Data Structures and Algorithms", "CS201", "Fundamental concepts of data organization and problem-solving techniques."),
                new Course("Operating Systems", "CS301", "Introduction to process management, memory management, and file systems."),
                new Course("Computer Networks", "CS302", "Basic principles of networking, including protocols, TCP/IP, and network security."),
                new Course("Database Management Systems", "CS303", "Concepts of relational databases, SQL, and data modeling."),
                new Course("Software Engineering", "CS304", "Principles of software development, including SDLC, design patterns, and testing."),
                new Course("Cybersecurity Fundamentals", "CS305", "Introduction to cybersecurity concepts, encryption, and threat mitigation.")
        });

        courseRepository.saveAll(courses);
    }
}
