package com.example.demo.repo;

import com.example.demo.entity.Student;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepo extends FakeJPA<Student> {
}