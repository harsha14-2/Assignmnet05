package com.a5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Entity class representing a course in the system.
 * Courses can have multiple students enrolled through a many-to-many relationship.
 */
@Entity
@Table(name = "courses")
public class Course {
    /**
     * Primary key for the course entity.
     * Auto-generated identity column.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    /**
     * Course name.
     * Required field that cannot be blank and must be unique.
     */
    @NotBlank(message = "Course name is required")
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Course description.
     * Optional field providing additional information about the course.
     */
    private String description;

    /**
     * Collection of students enrolled in this course.
     * Uses a many-to-many relationship with the Student entity, mapped by the courses field in Student.
     */
    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private Set<Student> students = new HashSet<>();

    /**
     * Maximum number of students allowed to enroll in this course.
     */
    @Column(name = "max_enrollment")
    private Integer maxEnrollment;  // Change from int to Integer

    /**
     * Students on the waitlist for this course if the max enrollment is reached.
     */
    @ManyToMany
    @JoinTable(
        name = "course_waitlist",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> waitlist = new HashSet<>();

    /**
     * Prerequisite courses that must be completed before enrolling in this course.
     */
    @ManyToMany
    @JoinTable(
        name = "course_prerequisites",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "prerequisite_id")
    )
    private Set<Course> prerequisites = new HashSet<>();

    /**
     * Start date of the enrollment period.
     */
    private String enrollmentStartDate;

    /**
     * End date of the enrollment period.
     */
    private String enrollmentEndDate;

    /**
     * Default constructor.
     * Initializes students collection as an empty HashSet.
     */
    public Course() {
        this.students = new HashSet<>();
    }

    /**
     * Gets the course's ID.
     * 
     * @return The course's ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the course's ID.
     * 
     * @param id The ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the course's name.
     * 
     * @return The course's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the course's name.
     * 
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the course's description.
     * 
     * @return The course's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the course's description.
     * 
     * @param description The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the set of students enrolled in this course.
     * 
     * @return A set of Student objects
     */
    public Set<Student> getStudents() {
        return students;
    }

    /**
     * Sets the students enrolled in this course.
     * 
     * @param students A set of Student objects
     */
    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    /**
     * Gets the maximum enrollment allowed for the course.
     * 
     * @return the maxEnrollment
     */
    public Integer getMaxEnrollment() {  // Change from int to Integer
        return maxEnrollment;
    }

    /**
     * Sets the maximum number of students allowed in the course.
     * 
     * @param maxEnrollment the maxEnrollment to set
     */
    public void setMaxEnrollment(Integer maxEnrollment) {  // Change from int to Integer
        this.maxEnrollment = maxEnrollment;
    }

    /**
     * Gets the waitlisted students.
     * 
     * @return a set of waitlisted students
     */
    public Set<Student> getWaitlist() {
        return waitlist;
    }

    /**
     * Sets the waitlist for the course.
     * 
     * @param waitlist the waitlist to set
     */
    public void setWaitlist(Set<Student> waitlist) {
        this.waitlist = waitlist;
    }

    /**
     * Gets the prerequisites for the course.
     * 
     * @return a set of prerequisite courses
     */
    public Set<Course> getPrerequisites() {
        return prerequisites;
    }

    /**
     * Sets the prerequisite courses for this course.
     * 
     * @param prerequisites the prerequisites to set
     */
    public void setPrerequisites(Set<Course> prerequisites) {
        this.prerequisites = prerequisites;
    }

    /**
     * Gets the enrollment start date.
     * 
     * @return the enrollment start date
     */
    public String getEnrollmentStartDate() {
        return enrollmentStartDate;
    }

    /**
     * Sets the enrollment start date.
     * 
     * @param enrollmentStartDate the start date to set
     */
    public void setEnrollmentStartDate(String enrollmentStartDate) {
        this.enrollmentStartDate = enrollmentStartDate;
    }

    /**
     * Gets the enrollment end date.
     * 
     * @return the enrollment end date
     */
    public String getEnrollmentEndDate() {
        return enrollmentEndDate;
    }

    /**
     * Sets the enrollment end date.
     * 
     * @param enrollmentEndDate the end date to set
     */
    public void setEnrollmentEndDate(String enrollmentEndDate) {
        this.enrollmentEndDate = enrollmentEndDate;
    }

    /**
     * Checks if this course is equal to another object.
     * Equality is based on ID, name, and description.
     * 
     * @param o The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) &&
               Objects.equals(name, course.name) &&
               Objects.equals(description, course.description);
    }

    /**
     * Generates a hash code for this course.
     * Based on ID, name, and description.
     * 
     * @return The hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    /**
     * Returns a string representation of this course.
     * Includes ID, name, and description.
     * 
     * @return A string representation of the course
     */
    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public boolean isFull() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isFull'");
    }
}
