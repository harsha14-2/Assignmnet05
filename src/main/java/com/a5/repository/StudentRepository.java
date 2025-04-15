package com.a5.repository;

import com.a5.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Student entities.
 * Extends JpaRepository to inherit basic CRUD operations and pagination support.
 * Provides custom queries for retrieving student data with eagerly fetched associations.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    /**
     * Checks if a student with the specified email already exists.
     * 
     * @param email The email to check
     * @return true if a student with the email exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Retrieves all students with their associated courses eagerly loaded.
     * Uses a JPQL query with LEFT JOIN FETCH to avoid N+1 query problems.
     * 
     * @return List of all students with course data
     */
    @Query("SELECT DISTINCT s FROM Student s LEFT JOIN FETCH s.courses")
    List<Student> findAllWithCourses();

    /**
     * Search by name or email (case-insensitive).
     */
    List<Student> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);

    /**
     * Retrieves a specific student by their ID with course data eagerly loaded.
     * Uses a JPQL query with LEFT JOIN FETCH to avoid N+1 query problems.
     * 
     * @param id The ID of the student to retrieve
     * @return An Optional containing the student if found, or empty if not found
     */
    @Query("SELECT DISTINCT s FROM Student s LEFT JOIN FETCH s.courses WHERE s.id = :id")
    Optional<Student> findByIdWithCourses(Long id);
}
