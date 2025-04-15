package com.a5.dto;

import java.time.LocalDate;
import java.util.Set;

public class StudentDTO {
    private Long id;
    private String name;
    private String email;
    private Set<String> enrolledCourses;
    private Set<String> waitlistedCourses;
    private Set<String> completedCourses;
    private LocalDate enrollmentDate;

    // Constructors
    public StudentDTO() {}

    public StudentDTO(Long id, String name, String email, Set<String> enrolledCourses,
                      Set<String> waitlistedCourses, Set<String> completedCourses,
                      LocalDate enrollmentDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.enrolledCourses = enrolledCourses;
        this.waitlistedCourses = waitlistedCourses;
        this.completedCourses = completedCourses;
        this.enrollmentDate = enrollmentDate;
    }

    // Getters and Setters
    // (Generated or use Lombok @Data for brevity)
    // ...
}
