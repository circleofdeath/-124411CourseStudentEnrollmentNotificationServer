package com.example.demo.repo;

import com.example.demo.entity.Course;
import org.springframework.stereotype.Repository;

@Repository
public class CourseRepo extends FakeJPA<Course> {
}