package com.example.demo.entity;

import com.example.demo.repo.FakeJPA;
import lombok.Data;

@Data
public class Course implements FakeJPA.JPAElement {
    private long id = -1;
    private String name;
    private String description;
    private String status;
}