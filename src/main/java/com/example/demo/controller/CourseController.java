package com.example.demo.controller;

import com.example.demo.dto.CourseDTO;
import com.example.demo.entity.Course;
import com.example.demo.entity.Student;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {
    @Autowired
    public CourseService courseService;

    @GetMapping("/enrollments/courses/{courseId}")
    public List<Student> getEnrollments(@PathVariable long courseId) {
        return courseService.getEnrollments(courseId);
    }

    @GetMapping("/get_course/{id}")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable long id) {
        CourseDTO course = courseService.getById(id);
        return course == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(course);
    }

    @GetMapping("/courses")
    public List<CourseDTO> getCourses() {
        return courseService.getAll().collectList().block();
    }

    @PutMapping("/add_course")
    public ResponseEntity<CourseDTO> addCourse(@RequestBody Course course) {
        if(course == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(courseService.save(course));
    }
}