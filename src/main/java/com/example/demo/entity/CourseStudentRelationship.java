package com.example.demo.entity;

import com.example.demo.repo.FakeJPA;
import lombok.Data;

@Data
public class CourseStudentRelationship implements FakeJPA.JPAElement {
    private long id = -1;
    private long courseID;
    private long studentID;
}