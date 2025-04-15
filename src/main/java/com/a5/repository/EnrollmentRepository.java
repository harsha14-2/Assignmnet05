package com.a5.repository;

import com.a5.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Enrollment entities.
 * Extends JpaRepository to inherit basic CRUD operations and pagination support.
 * Provides custom queries for retrieving enrollment data with eagerly fetched associations.
 */
@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    /**
     * Retrieves all enrollments with their associated student and course entities eagerly loaded.
     * Uses a JPQL query with LEFT JOIN FETCH to avoid N+1 query problems.
     * 
     * @return List of all enrollments with student and course data
     */
    @Query("SELECT e FROM Enrollment e LEFT JOIN FETCH e.student LEFT JOIN FETCH e.course")
    List<Enrollment> findAllWithDetails();

    /**
     * Retrieves all enrollments for a specific student with course data eagerly loaded.
     * Uses a JPQL query with LEFT JOIN FETCH to avoid N+1 query problems.
     * 
     * @param studentId The ID of the student whose enrollments to retrieve
     * @return List of enrollments for the specified student
     */
    @Query("SELECT e FROM Enrollment e LEFT JOIN FETCH e.student LEFT JOIN FETCH e.course WHERE e.student.id = :studentId")
    List<Enrollment> findByStudentId(Long studentId);

    /**
     * Retrieves all enrollments for a specific course with student data eagerly loaded.
     * Uses a JPQL query with LEFT JOIN FETCH to avoid N+1 query problems.
     * 
     * @param courseId The ID of the course for which to retrieve enrollments
     * @return List of enrollments for the specified course
     */
    @Query("SELECT e FROM Enrollment e LEFT JOIN FETCH e.student LEFT JOIN FETCH e.course WHERE e.course.id = :courseId")
    List<Enrollment> findByCourseId(Long courseId);

    /**
     * Checks if an enrollment exists for the specified student and course.
     * 
     * @param studentId The ID of the student
     * @param courseId The ID of the course
     * @return true if the student is enrolled in the course, false otherwise
     */
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
} 