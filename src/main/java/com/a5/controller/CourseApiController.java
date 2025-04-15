package com.a5.controller;

import com.a5.model.Course;
import com.a5.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST API Controller for managing course resources.
 * Provides endpoints for CRUD operations on course data.
 */
@RestController
@RequestMapping("/api/courses")
public class CourseApiController {

    @Autowired
    private CourseService courseService;

    /**
     * Retrieves all courses from the database.
     * 
     * @return List of all courses
     */
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    /**
     * Retrieves a specific course by its ID.
     * 
     * @param id The course ID to look up
     * @return ResponseEntity containing the course if found, or a 404 Not Found status
     */
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {
        return courseService.getCourseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new course.
     * 
     * @param course The course object to create, validated using Bean Validation
     * @return The created course with assigned ID
     */
    @PostMapping
    public Course createCourse(@Valid @RequestBody Course course) {
        return courseService.saveCourse(course);
    }

    /**
     * Updates an existing course.
     * 
     * @param id The ID of the course to update
     * @param course The updated course details, validated using Bean Validation
     * @return ResponseEntity containing the updated course if found, or a 404 Not Found status
     */
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @Valid @RequestBody Course course) {
        if (!courseService.getCourseById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        course.setId(id);
        return ResponseEntity.ok(courseService.saveCourse(course));
    }

    /**
     * Deletes a course.
     * 
     * @param id The ID of the course to delete
     * @return ResponseEntity with empty body and 200 OK status if successful, or 404 Not Found if the course doesn't exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        if (!courseService.getCourseById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        courseService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }
} 