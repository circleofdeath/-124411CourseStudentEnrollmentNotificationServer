package com.example.demo.dto;

import com.example.demo.entity.Student;
import lombok.Data;

import java.util.List;

@Data
public class CourseDTO {
    private long id = -1;
    private String name;
    private String description;
    private String status;
    private List<Student> studentList;
}