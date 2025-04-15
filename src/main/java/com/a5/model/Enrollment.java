package com.a5.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * Entity class representing an enrollment of a student in a course.
 * Forms the many-to-many relationship between Student and Course entities.
 */
@Entity
@Table(name = "enrollments")
@Getter
@Setter
public class Enrollment {
    /**
     * Primary key for the enrollment entity.
     * Auto-generated identity column.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The student who is enrolled in the course.
     * Many-to-one relationship with the Student entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    /**
     * The course in which the student is enrolled.
     * Many-to-one relationship with the Course entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    /**
     * The date and time when the enrollment was created.
     * Automatically set to the current date and time.
     */
    @Column(name = "enrollment_date", nullable = false)
    private LocalDateTime enrollmentDate = LocalDateTime.now();

    /**
     * Default constructor.
     * Initializes the enrollment date to the current date and time.
     */
    public Enrollment() {
        this.enrollmentDate = LocalDateTime.now();
    }

    /**
     * Constructor with student and course.
     * Initializes the enrollment with the specified student and course,
     * and sets the enrollment date to the current date and time.
     * 
     * @param student The student to enroll
     * @param course The course in which to enroll the student
     */
    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = LocalDateTime.now();
    }

    /**
     * Gets the enrollment's ID.
     * 
     * @return The enrollment's ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the enrollment's ID.
     * 
     * @param id The ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the student associated with this enrollment.
     * 
     * @return The enrolled student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Sets the student for this enrollment.
     * 
     * @param student The student to set
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Gets the course associated with this enrollment.
     * 
     * @return The course in which the student is enrolled
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Sets the course for this enrollment.
     * 
     * @param course The course to set
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Gets the enrollment date.
     * 
     * @return The date and time when the enrollment was created
     */
    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }

    /**
     * Sets the enrollment date.
     * 
     * @param enrollmentDate The enrollment date to set
     */
    public void setEnrollmentDate(LocalDateTime enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
} 