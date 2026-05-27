package spring_boot.session12ex02.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring_boot.session12ex02.model.Course;
import spring_boot.session12ex02.service.CourseService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
public class CourseServiceImpl implements CourseService {
    private final List<Course> courses = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public List<Course> findAll() {
        return courses;
    }

    @Override
    public Course findById(Long id) {
        Course course = courses.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (course == null) {
            log.warn("Course with id {} not found", id);
        }

        return course;
    }

    @Override
    public Course save(Course course) {

        course.setId(idGenerator.incrementAndGet());

        courses.add(course);

        log.info("Created new course successfully: {}", course.getCourseName());

        return course;
    }

    @Override
    public Course update(Long id, Course course) {

        Course oldCourse = findById(id);

        if (oldCourse == null) {
            return null;
        }

        oldCourse.setCourseName(course.getCourseName());
        oldCourse.setInstructor(course.getInstructor());
        oldCourse.setDurationHours(course.getDurationHours());
        oldCourse.setFee(course.getFee());

        log.info("Updated course with id {}", id);

        return oldCourse;
    }

    @Override
    public void delete(Long id) {

        Course course = findById(id);

        if (course != null) {

            courses.remove(course);

            log.info("Deleted course with id {}", id);
        }
    }
}
