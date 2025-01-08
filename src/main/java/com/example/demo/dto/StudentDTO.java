package com.example.demo.dto;

import com.example.demo.entity.Course;
import lombok.Data;

import java.util.List;

@Data
public class StudentDTO {
    private long id = -1;
    private int age;
    private String name;
    private List<Course> courseList;
    private List<String> unread;
}