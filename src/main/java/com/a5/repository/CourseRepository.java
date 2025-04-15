package com.a5.repository;

import com.a5.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Course entities.
 * Extends JpaRepository to inherit basic CRUD operations and pagination support.
 * Provides custom queries for retrieving course data with eagerly fetched associations.
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    /**
     * Checks if a course with the specified name already exists.
     * 
     * @param name The course name to check
     * @return true if a course with the name exists, false otherwise
     */
    boolean existsByName(String name);

    /**
     * Retrieves all courses with their associated students eagerly loaded.
     * Uses a JPQL query with LEFT JOIN FETCH to avoid N+1 query problems.
     * 
     * @return List of all courses with student data
     */
    @Query("SELECT DISTINCT c FROM Course c LEFT JOIN FETCH c.students")
    List<Course> findAllWithStudents();
} 