package com.a5.controller;

import com.a5.dto.StudentDTO;
import com.a5.model.Student;
import com.a5.repository.StudentRepository;
import com.a5.service.StudentService;
import com.a5.service.CourseService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseService courseService;

    @GetMapping
    public String listStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String course,
            @RequestParam(required = false) String status,
            Model model) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Student> studentsPage;
        if ((keyword != null && !keyword.isEmpty()) ||
            (course != null && !course.isEmpty()) ||
            (status != null && !status.isEmpty())) {
            studentsPage = studentService.searchStudents(keyword, course, status, pageable);
        } else {
            studentsPage = studentRepository.findAll(pageable);
        }

        model.addAttribute("studentsPage", studentsPage);
        model.addAttribute("students", studentsPage.getContent());
        model.addAttribute("keyword", keyword);
        model.addAttribute("course", course);
        model.addAttribute("status", status);
        model.addAttribute("param", Map.of("sortBy", sortBy));
        model.addAttribute("availableCourses", courseService.getAllCourses());

        return "students/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("availableCourses", courseService.getAllCourses());
        return "students/form";
    }

    @PostMapping
    public String createStudent(@Valid @ModelAttribute("student") Student student,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("availableCourses", courseService.getAllCourses());
            return "students/form";
        }

        if (studentService.existsByEmail(student.getEmail())) {
            result.rejectValue("email", "error.student", "Email already exists");
            model.addAttribute("availableCourses", courseService.getAllCourses());
            return "students/form";
        }

        studentService.saveStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id: " + id));
        model.addAttribute("student", student);
        model.addAttribute("availableCourses", courseService.getAllCourses());
        return "students/form";
    }

    @PostMapping("/{id}")
    public String updateStudent(@PathVariable Long id,
                                @Valid @ModelAttribute("student") Student student,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("availableCourses", courseService.getAllCourses());
            return "students/form";
        }

        student.setId(id);
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    @PostMapping("/{id}/delete")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }

    @GetMapping("/dto")
    @ResponseBody
    public List<StudentDTO> getAllStudentDTOs() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private StudentDTO convertToDTO(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getCourses().stream().map(c -> c.getName()).collect(Collectors.toSet()),
                student.getWaitlistedCourses().stream().map(c -> c.getName()).collect(Collectors.toSet()),
                student.getCompletedCourses().stream().map(c -> c.getName()).collect(Collectors.toSet()),
                student.getEnrollmentDate() != null ? student.getEnrollmentDate().toLocalDate() : null
        );
    }
}
