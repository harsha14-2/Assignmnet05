# Student Course Management System

A Spring Boot application for managing students, courses, and enrollments.

## Features

- CRUD operations for students and courses
- Enrollment management
- Real-time monitoring with Spring Boot Actuator
- Error handling with custom error pages
- Database persistence with MySQL
- Thymeleaf templates for web interface

## Technologies

- Java 17
- Spring Boot 3.2.3
- MySQL 8.x
- Hibernate/JPA
- Thymeleaf
- Spring Boot Actuator
- Micrometer with Prometheus

## Prerequisites

- Java 17 or higher
- MySQL 8.x
- Maven 3.x

## Setup

1. Clone the repository form https://github.com/rebootthemodem/StudentCourseManagementSystem
2. Configure MySQL database in `application.properties`
3. Build the project:
   ```bash
   ./mvnw clean install
   ```

## Running the Application

1. Start the application:
   ```bash
   ./mvnw spring-boot:run
   ```
2. Access the web interface at: `http://localhost:9000`

## API Endpoints

### Students
- GET `/api/students` - Get all students
- GET `/api/students/{id}` - Get student by ID
- POST `/api/students` - Create a new student
- PUT `/api/students/{id}` - Update a student
- DELETE `/api/students/{id}` - Delete a student

### Courses
- GET `/api/courses` - Get all courses
- GET `/api/courses/{id}` - Get course by ID
- POST `/api/courses` - Create a new course
- PUT `/api/courses/{id}` - Update a course
- DELETE `/api/courses/{id}` - Delete a course

### Enrollments
- GET `/api/enrollments` - Get all enrollments
- GET `/api/enrollments/student/{studentId}` - Get enrollments by student ID
- GET `/api/enrollments/course/{courseId}` - Get enrollments by course ID
- POST `/api/enrollments?studentId={id}&courseId={id}` - Create a new enrollment
- DELETE `/api/enrollments?studentId={id}&courseId={id}` - Delete an enrollment

## Monitoring

Access monitoring endpoints at:
- Health check: `http://localhost:8080/actuator/health`
- Metrics: `http://localhost:8080/actuator/metrics`
- Prometheus metrics: `http://localhost:8080/actuator/prometheus`

## Features Implemented (Assignment 5 - CSD 4464)

1. Pagination and Sorting  
4. Search Functionality  
14. Course Scheduling Features  
23. Support Mobile Access


## Database Schema

```sql
CREATE TABLE students (
    student_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE courses (
    course_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT
);

CREATE TABLE enrollments (
    enrollment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT,
    course_id BIGINT,
    enrollment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
);
```

## Testing

Run tests using:
```bash
./mvnw test
```

## Error Handling

The application includes custom error pages for:
- 400 Bad Request
- 403 Forbidden
- 404 Not Found
- 500 Internal Server Error

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details. 