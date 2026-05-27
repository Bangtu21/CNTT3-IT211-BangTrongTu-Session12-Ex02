package spring_boot.session12ex02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring_boot.session12ex02.model.Course;
import spring_boot.session12ex02.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@Slf4j
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        log.info("GET request to /api/courses");
        return ResponseEntity.ok(courseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        log.info("GET request to /api/courses/{}", id);
        try {
            Course course = courseService.findById(id);
            if (course == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Course not found");
            }

            return ResponseEntity.ok(course);

        } catch (RuntimeException e) {

            log.error("Error while getting course by id {}", id);

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error");
        }
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        log.info("POST request to /api/courses");
        return new ResponseEntity<>(
                courseService.save(course),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(
            @PathVariable Long id,
            @RequestBody Course course
    ) {

        log.info("PUT request to /api/courses/{}", id);

        Course updatedCourse = courseService.update(id, course);

        if (updatedCourse == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Course not found");
        }

        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(
            @PathVariable Long id
    ) {

        log.info("DELETE request to /api/courses/{}", id);

        Course course = courseService.findById(id);

        if (course == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Course not found");
        }

        courseService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
