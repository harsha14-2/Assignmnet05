package com.a5.controller;

import com.a5.model.Enrollment;
import com.a5.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API Controller for managing enrollment resources.
 * Provides endpoints for creating, retrieving, and deleting enrollments between students and courses.
 */
@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentApiController {

    @Autowired
    private EnrollmentService enrollmentService;

    /**
     * Retrieves all enrollments from the database.
     * 
     * @return List of all enrollments
     */
    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }

    /**
     * Retrieves all enrollments for a specific student.
     * 
     * @param studentId The ID of the student whose enrollments to retrieve
     * @return List of enrollments for the specified student
     */
    @GetMapping("/student/{studentId}")
    public List<Enrollment> getEnrollmentsByStudent(@PathVariable Long studentId) {
        return enrollmentService.getEnrollmentsByStudentId(studentId);
    }

    /**
     * Retrieves all enrollments for a specific course.
     * 
     * @param courseId The ID of the course to retrieve enrollments for
     * @return List of enrollments for the specified course
     */
    @GetMapping("/course/{courseId}")
    public List<Enrollment> getEnrollmentsByCourse(@PathVariable Long courseId) {
        return enrollmentService.getEnrollmentsByCourseId(courseId);
    }

    /**
     * Creates a new enrollment by enrolling a student in a course.
     * 
     * @param studentId The ID of the student to enroll
     * @param courseId The ID of the course in which to enroll the student
     * @return The created enrollment with assigned ID
     * @throws IllegalArgumentException if the student or course is not found
     * @throws IllegalStateException if the student is already enrolled in the course
     */
    @PostMapping
    public Enrollment createEnrollment(@RequestParam Long studentId, @RequestParam Long courseId) {
        return enrollmentService.enrollStudent(studentId, courseId);
    }

    /**
     * Deletes an enrollment by unenrolling a student from a course.
     * 
     * @param studentId The ID of the student to unenroll
     * @param courseId The ID of the course from which to unenroll the student
     * @return ResponseEntity with empty body and 200 OK status
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteEnrollment(@RequestParam Long studentId, @RequestParam Long courseId) {
        enrollmentService.unenrollStudent(studentId, courseId);
        return ResponseEntity.ok().build();
    }
} 