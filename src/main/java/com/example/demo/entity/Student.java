package com.example.demo.entity;

import com.example.demo.repo.FakeJPA;
import lombok.Data;

import java.util.List;

@Data
public class Student implements FakeJPA.JPAElement {
    private long id = -1;
    private int age;
    private String name;
    private List<String> unread;
}