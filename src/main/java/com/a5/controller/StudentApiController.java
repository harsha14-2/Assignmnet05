package com.a5.controller;

import com.a5.model.Student;
import com.a5.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST API Controller for managing student resources.
 * Provides endpoints for CRUD operations on student data.
 */
@RestController
@RequestMapping("/api/students")
public class StudentApiController {

    @Autowired
    private StudentService studentService;

    /**
     * Retrieves all students from the database.
     * 
     * @return List of all students
     */
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    /**
     * Retrieves a specific student by their ID.
     * 
     * @param id The student ID to look up
     * @return ResponseEntity containing the student if found, or a 404 Not Found status
     */
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new student.
     * 
     * @param student The student object to create, validated using Bean Validation
     * @return The created student with assigned ID
     */
    @PostMapping
    public Student createStudent(@Valid @RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    /**
     * Updates an existing student.
     * 
     * @param id The ID of the student to update
     * @param student The updated student details, validated using Bean Validation
     * @return ResponseEntity containing the updated student if found, or a 404 Not Found status
     */
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @Valid @RequestBody Student student) {
        if (!studentService.getStudentById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        student.setId(id);
        return ResponseEntity.ok(studentService.saveStudent(student));
    }

    /**
     * Deletes a student.
     * 
     * @param id The ID of the student to delete
     * @return ResponseEntity with empty body and 200 OK status if successful, or 404 Not Found if the student doesn't exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (!studentService.getStudentById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
} 