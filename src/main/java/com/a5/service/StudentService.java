package com.a5.service;

import com.a5.model.Student;
import com.a5.model.Course;
import com.a5.repository.StudentRepository;
import com.a5.repository.CourseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findAllWithCourses();
    }

    // ✅ Working filter + pagination + sorting
    public Page<Student> searchStudents(String keyword, String course, String status, Pageable pageable) {
        List<Student> all = studentRepository.findAllWithCourses(); // Avoid lazy loading

        // Stream filter
        List<Student> filtered = all.stream()
                .filter(s -> {
                    boolean match = true;

                    if (keyword != null && !keyword.isEmpty()) {
                        String lower = keyword.toLowerCase();
                        match &= s.getName().toLowerCase().contains(lower)
                              || s.getEmail().toLowerCase().contains(lower);
                    }

                    if (course != null && !course.isEmpty()) {
                        boolean inCourse = s.getCourses().stream().anyMatch(c -> c.getName().equalsIgnoreCase(course)) ||
                                           s.getWaitlistedCourses().stream().anyMatch(c -> c.getName().equalsIgnoreCase(course)) ||
                                           s.getCompletedCourses().stream().anyMatch(c -> c.getName().equalsIgnoreCase(course));
                        match &= inCourse;
                    }

                    if (status != null && !status.isEmpty()) {
                        switch (status.toLowerCase()) {
                            case "active":
                                match &= !s.getCourses().isEmpty();
                                break;
                            case "waitlisted":
                                match &= !s.getWaitlistedCourses().isEmpty();
                                break;
                            case "completed":
                                match &= !s.getCompletedCourses().isEmpty();
                                break;
                            default:
                                break;
                        }
                    }

                    return match;
                })
                .collect(Collectors.toList());

        // Manual pagination (subList)
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filtered.size());
        List<Student> pageContent = start > end ? new ArrayList<>() : filtered.subList(start, end);

        return new PageImpl<>(pageContent, pageable, filtered.size());
    }

    @Transactional(readOnly = true)
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findByIdWithCourses(id);
    }

    @Transactional
    public Student saveStudent(Student student) {
        if (student.getEnrollmentDate() == null) {
            student.setEnrollmentDate(LocalDate.now().atStartOfDay()); // ✅ FIXED HERE
        }

        if (student.getCourses() == null) {
            student.setCourses(new HashSet<>());
        }
        if (student.getWaitlistedCourses() == null) {
            student.setWaitlistedCourses(new HashSet<>());
        }
        if (student.getCompletedCourses() == null) {
            student.setCompletedCourses(new HashSet<>());
        }

        return studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public boolean existsByEmail(String email) {
        return studentRepository.existsByEmail(email);
    }

    @Transactional
    public void enrollStudentInCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + studentId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID: " + courseId));

        if (course.isFull()) {
            student.getWaitlistedCourses().add(course);
        } else {
            student.getCourses().add(course);
        }

        studentRepository.save(student);
    }

    @Transactional
    public void completeCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + studentId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID: " + courseId));

        if (student.getCourses().contains(course)) {
            student.getCourses().remove(course);
            student.getCompletedCourses().add(course);
            studentRepository.save(student);
        } else {
            throw new IllegalArgumentException("Student is not enrolled in the specified course.");
        }
    }
}
