package com.a5.service;

import com.a5.model.Course;
import com.a5.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing course data.
 * Provides methods for CRUD operations on course entities.
 */
@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    /**
     * Retrieves all courses with their associated students.
     * 
     * @return List of all courses with eagerly loaded student data
     */
    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        return courseRepository.findAllWithStudents();
    }

    /**
     * Retrieves a specific course by its ID.
     * 
     * @param id The ID of the course to retrieve
     * @return An Optional containing the course if found, or empty if not found
     */
    @Transactional(readOnly = true)
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    /**
     * Saves a course entity.
     * Can be used for both creating new courses and updating existing ones.
     * 
     * @param course The course to save
     * @return The saved course with assigned ID
     */
    @Transactional
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    /**
     * Deletes a course by its ID.
     * 
     * @param id The ID of the course to delete
     */
    @Transactional
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    /**
     * Checks if a course with the specified name already exists.
     * 
     * @param name The course name to check
     * @return true if a course with the name exists, false otherwise
     */
    public boolean existsByName(String name) {
        return courseRepository.existsByName(name);
    }
} 