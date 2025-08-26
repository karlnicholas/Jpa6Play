# Coding Assignment: Build a Spring Boot REST API with JPA for a School Management System

## Objective
Develop a Spring Boot application with a REST API to manage a school system, including schools, students, courses, and instructors. Use Spring Data JPA for persistence with an H2 in-memory database. Implement CRUD operations, entity relationships, and specific business logic (e.g., adding students to courses) to match the functionality of a school management system. Ensure the application produces working code that generates efficient SQL queries, particularly for the "Add a student to a course" endpoint, to minimize database load and optimize performance. Use DTOs for data transfer and ensure proper error handling and validation.

## Requirements

### 1. Project Setup
- **Objective**: Set up a Maven-based Spring Boot project with necessary dependencies and configuration.
- **Tasks**:
    - Create a Maven project with Spring Boot version 3.5.4 and Java 21.
    - Include the following dependencies in `pom.xml`:
        - `spring-boot-starter-data-jpa`
        - `spring-boot-starter-web`
        - `h2` (runtime scope)
        - `lombok`
    - Configure an H2 in-memory database in `application.properties` or `application.yml` with settings for a database named `schooldb`, H2 driver, default credentials (username `sa`, empty password), H2 dialect, and automatic schema updates.

### 2. Entities
- **Objective**: Implement JPA entities for `School`, `Student`, `Course`, and `Instructor` with fields and relationships, using Lombok for getters, setters, constructors, and `toString` methods, with all JPA relationships marked with `fetchType=FetchType.LAZY` unless the annotation is redundant (e.g., on the non-owning side of a relationship where it’s already defined by the owning side).
- **Tasks**:
    - Implement entities in the `org.example.model` package. Use of Lombok is allowed for getters, setters, no-arg and all-arg constructors, and `toString` methods.
    - Ensure all JPA relationships are marked with `fetchType=FetchType.LAZY` unless the annotation is redundant (e.g., on the non-owning side of a relationship).
    - Define the following entities (ownership of relationships to be determined by the assignee):
        - **School**:
            - Fields: `id` (auto-generated `Long`), `name` (String).
            - Relationships: One-to-many with `Course`, `Student`, and `Instructor`.
        - **Student**:
            - Fields: `id` (auto-generated `Long`), `name` (String).
            - Relationships: Many-to-many with `Course`, many-to-one with `School`.
        - **Course**:
            - Fields: `id` (auto-generated `Long`), `name` (String).
            - Relationships: Many-to-many with `Student`, many-to-one with `Instructor`, many-to-one with `School`.
        - **Instructor**:
            - Fields: `id` (auto-generated `Long`), `name` (String).
            - Relationships: One-to-many with `Course`, many-to-one with `School`.

### 3. DTOs
- **Objective**: Implement DTO classes as records for API responses.
- **Tasks**:
    - Create the following DTOs in the `org.example.dto` package:
        - `SchoolDto`: `Long id`, `String name`, `List<InstructorDto> instructors`, `List<CourseDto> courses`, `List<StudentDto> students`.
        - `StudentDto`: `Long id`, `String name`.
        - `StudentDetailDto`: `Long id`, `String name`, `List<CourseDto> courses`.
        - `CourseDto`: `Long id`, `String name`.
        - `CourseDetailDto`: `Long id`, `String name`, `InstructorDto instructor`, `List<StudentDto> students`.
        - `InstructorDto`: `Long id`, `String name`.
        - `InstructorDetailDto`: `Long id`, `String name`, `List<CourseDto> courses`.

### 4. Repositories
- **Objective**: Implement Spring Data JPA repositories to support data access.
- **Tasks**:
    - Create repositories in the `org.example.repository` package, each extending `JpaRepository`.
    - Implement repositories for `School`, `Student`, `Course`, and `Instructor` with custom query methods as needed to support the service layer and optimize SQL queries, particularly for the "Add a student to a course" endpoint (to be determined by the assignee).

### 5. Services
- **Objective**: Implement service classes for CRUD operations and specific business logic.
- **Tasks**:
    - Create service classes in the `org.example.service` package to handle business logic for `School`, `Student`, `Course`, and `Instructor`.
    - Implement methods to support CRUD operations and adding students to courses, ensuring proper error handling (e.g., throwing exceptions for missing required fields like `school`) and transactional operations where necessary, with efficient SQL query generation, especially for adding students to courses (to be determined by the assignee).

### 6. REST Controllers
- **Objective**: Implement REST controllers to expose CRUD operations and specific actions via a REST API.
- **Tasks**:
    - Create controllers in the `org.example.api` package, annotated with the appropriate Spring annotations for REST controllers and request mappings.
    - Use `ResponseEntity` for all responses, returning appropriate HTTP status codes (e.g., 200 OK, 201 Created, 404 Not Found).
    - Implement the following controllers:
        - **SchoolController** (`/api/school`):
            - `GET /{name}`: Retrieve a school by name, returning `SchoolDto` or 404 if not found.
        - **StudentController** (`/api/student`):
            - `POST`: Create a student, returning `StudentDto`.
            - `GET /{name}`: Retrieve a student by name, returning `StudentDetailDto` or 404 if not found.
            - `GET`: Retrieve all students, returning `List<StudentDto>`.
            - `PUT /{name}`: Update a student by name, returning `StudentDto` or 404 if not found.
            - `DELETE /{name}`: Delete a student by name, returning 200 if successful or 404 if not found.
        - **CourseController** (`/api/course`):
            - `POST`: Create a course, returning `CourseDto`.
            - `GET /{name}`: Retrieve a course by name, returning `CourseDetailDto` or 404 if not found.
            - `GET`: Retrieve all courses, returning `List<CourseDto>`.
            - `PUT /{name}`: Update a course by name, returning `CourseDto` or 404 if not found.
            - `DELETE /{name}`: Delete a course by name, returning 200 if successful or 404 if not found.
            - `POST /{courseName}/students/{studentName}`: Add a student to a course, returning 200 if successful, optimized for efficient SQL queries.
        - **InstructorController** (`/api/instructor`):
            - `POST`: Create an instructor, returning `InstructorDto`.
            - `GET /{name}`: Retrieve an instructor by name, returning `InstructorDetailDto` or 404 if not found.
            - `GET`: Retrieve all instructors, returning `List<InstructorDto>`.
            - `PUT /{name}`: Update an instructor by name, returning `InstructorDto` or 404 if not found.
            - `DELETE /{name}`: Delete an instructor by name, returning 200 if successful or 404 if not found.

### 7. Deliverables
- **Objective**: Provide a complete, runnable application with documentation.
- **Tasks**:
    - Submit the source code, organized in packages: `org.example.model`, `org.example.dto`, `org.example.repository`, `org.example.service`, `org.example.api`.
    - Include a `README.md` with:
        - Instructions to run the application (e.g., using Maven).
        - Example API requests for each endpoint.
        - Justification of the SQL generated, explaining why it is minimal and efficient, particularly for the `POST /{courseName}/students/{studentName}` endpoint.
    - Ensure the application starts without errors and supports all specified functionality with efficient SQL queries.

### 8. Notes
- Ensure proper error handling (e.g., throw exceptions for missing required fields like `school`).
- Use transactional operations for modifying relationships (e.g., adding a student to a course).
- Optimize SQL queries, especially for the `POST /{courseName}/students/{studentName}` endpoint, to minimize database load.
- Test the application with sample data to verify relationships (e.g., a school with multiple students, courses, and instructors).