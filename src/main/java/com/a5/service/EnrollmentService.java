package com.a5.service;

import com.a5.model.Enrollment;
import com.a5.model.Student;
import com.a5.model.Course;
import com.a5.repository.EnrollmentRepository;
import com.a5.repository.StudentRepository;
import com.a5.repository.CourseRepository;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing student enrollments in courses.
 * Provides methods for enrolling/unenrolling students and retrieving enrollment information.
 * Includes metrics collection for monitoring enrollment operations.
 */
@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private CourseRepository courseRepository;

    /** Counter for tracking the number of enrollment operations */
    private final Counter enrollmentCounter;
    
    /** Counter for tracking the number of unenrollment operations */
    private final Counter unenrollmentCounter;

    /**
     * Constructor that initializes metric counters for enrollment operations.
     * 
     * @param registry The Micrometer registry for registering metrics
     */
    public EnrollmentService(MeterRegistry registry) {
        this.enrollmentCounter = Counter.builder("app.enrollments")
                .description("Number of student enrollments")
                .register(registry);
        this.unenrollmentCounter = Counter.builder("app.unenrollments")
                .description("Number of student unenrollments")
                .register(registry);
    }

    /**
     * Retrieves all enrollments with student and course details.
     * 
     * @return List of all enrollments
     */
    @Transactional(readOnly = true)
    @Timed(value = "enrollment.list.time", description = "Time taken to list all enrollments")
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAllWithDetails();
    }

    /**
     * Retrieves all enrollments for a specific student.
     * 
     * @param studentId The ID of the student
     * @return List of enrollments for the specified student
     */
    @Transactional(readOnly = true)
    @Timed(value = "enrollment.student.list.time", description = "Time taken to list student enrollments")
    public List<Enrollment> getEnrollmentsByStudentId(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    /**
     * Retrieves all enrollments for a specific course.
     * 
     * @param courseId The ID of the course
     * @return List of enrollments for the specified course
     */
    @Transactional(readOnly = true)
    @Timed(value = "enrollment.course.list.time", description = "Time taken to list course enrollments")
    public List<Enrollment> getEnrollmentsByCourseId(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }

    /**
     * Enrolls a student in a course.
     * 
     * @param studentId The ID of the student to enroll
     * @param courseId The ID of the course in which to enroll the student
     * @return The created enrollment
     * @throws IllegalStateException if the student is already enrolled in the course
     * @throws IllegalArgumentException if the student or course is not found
     */
    @Transactional
    @Timed(value = "enrollment.create.time", description = "Time taken to enroll a student")
    public Enrollment enrollStudent(Long studentId, Long courseId) {
        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new IllegalStateException("Student is already enrolled in this course");
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        Enrollment enrollment = new Enrollment(student, course);
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        enrollmentCounter.increment();
        return savedEnrollment;
    }

    /**
     * Unenrolls a student from a course.
     * 
     * @param studentId The ID of the student to unenroll
     * @param courseId The ID of the course from which to unenroll the student
     */
    @Transactional
    @Timed(value = "enrollment.delete.time", description = "Time taken to unenroll a student")
    public void unenrollStudent(Long studentId, Long courseId) {
        enrollmentRepository.findByStudentId(studentId).stream()
                .filter(e -> e.getCourse().getId().equals(courseId))
                .findFirst()
                .ifPresent(enrollment -> {
                    enrollmentRepository.delete(enrollment);
                    unenrollmentCounter.increment();
                });
    }
} 