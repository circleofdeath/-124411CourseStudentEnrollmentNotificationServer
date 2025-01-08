package com.example.demo.repo;

import com.example.demo.entity.CourseStudentRelationship;
import org.springframework.stereotype.Repository;

@Repository
public class CourseStudentRelationshipRepo extends FakeJPA<CourseStudentRelationship> {
    public CourseStudentRelationship findByLink(long courseId, long studentId) {
        return data.filter(e -> e.getCourseID() == courseId && e.getStudentID() == studentId).singleOrEmpty().block();
    }
}