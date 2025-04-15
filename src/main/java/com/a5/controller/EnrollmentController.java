package com.a5.controller;

import com.a5.model.Student;
import com.a5.model.Course;
import com.a5.model.Enrollment;
import com.a5.service.StudentService;
import com.a5.service.CourseService;
import com.a5.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Web controller for managing student enrollments via the web interface.
 * Handles requests for viewing, creating, and deleting enrollments.
 * Uses Thymeleaf templates for rendering views.
 */
@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentController.class);
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private CourseService courseService;

    @Autowired
    private EnrollmentService enrollmentService;

    /**
     * Displays the enrollment list page with all enrollments, students, and courses.
     * 
     * @param model The Spring MVC model for passing data to the view
     * @return The name of the view template to render
     */
    @GetMapping
    public String listEnrollments(Model model) {
        var students = studentService.getAllStudents();
        var courses = courseService.getAllCourses();
        var enrollments = enrollmentService.getAllEnrollments();
        
        // Debug logging
        logger.info("Number of students: {}", students.size());
        logger.info("Number of enrollments: {}", enrollments.size());
        
        model.addAttribute("students", students);
        model.addAttribute("courses", courses);
        model.addAttribute("enrollments", enrollments);
        return "enrollments/list";
    }

    /**
     * Handles the form submission for enrolling a student in a course.
     * 
     * @param studentId The ID of the student to enroll
     * @param courseId The ID of the course in which to enroll the student
     * @param model The Spring MVC model for passing data to the view
     * @return A redirect to the enrollment list page on success, or the list page with an error message on failure
     */
    @PostMapping("/enroll")
    public String enrollStudent(@RequestParam Long studentId, @RequestParam Long courseId, Model model) {
        logger.info("Enrolling student {} in course {}", studentId, courseId);
        
        try {
            enrollmentService.enrollStudent(studentId, courseId);
            logger.info("Successfully enrolled student {} in course {}", studentId, courseId);
            return "redirect:/enrollments";
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("students", studentService.getAllStudents());
            model.addAttribute("courses", courseService.getAllCourses());
            model.addAttribute("enrollments", enrollmentService.getAllEnrollments());
            return "enrollments/list";
        }
    }

    /**
     * Handles the form submission for unenrolling a student from a course.
     * 
     * @param studentId The ID of the student to unenroll
     * @param courseId The ID of the course from which to unenroll the student
     * @return A redirect to the enrollment list page
     */
    @PostMapping("/unenroll")
    public String unenrollStudent(@RequestParam Long studentId, @RequestParam Long courseId) {
        logger.info("Unenrolling student {} from course {}", studentId, courseId);
        enrollmentService.unenrollStudent(studentId, courseId);
        logger.info("Successfully unenrolled student {} from course {}", studentId, courseId);
        return "redirect:/enrollments";
    }
} 