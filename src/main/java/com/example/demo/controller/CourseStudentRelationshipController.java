package com.example.demo.controller;

import com.example.demo.entity.CourseStudentRelationship;
import com.example.demo.repo.CourseRepo;
import com.example.demo.repo.CourseStudentRelationshipRepo;
import com.example.demo.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseStudentRelationshipController {
    @Autowired
    public CourseRepo courseRepo;
    @Autowired
    public StudentRepo studentRepo;
    @Autowired
    public CourseStudentRelationshipRepo manyToManyRepo;

    @GetMapping("/link/{student_id}/{course_id}")
    public ResponseEntity<CourseStudentRelationship> link(@PathVariable long student_id, @PathVariable long course_id) {
        if(courseRepo.findById(course_id) == null || studentRepo.findById(student_id) == null || manyToManyRepo.findByLink(course_id, student_id) != null) {
            return ResponseEntity.notFound().build();
        }

        CourseStudentRelationship relationship = new CourseStudentRelationship();
        relationship.setCourseID(course_id);
        relationship.setStudentID(student_id);
        manyToManyRepo.save(relationship);
        return ResponseEntity.ok(relationship);
    }
}