package com.example.demo.controller;

import com.example.demo.dto.StudentDTO;
import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {
    @Autowired
    public StudentService studentService;

    @GetMapping("/get_student/{id}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable long id) {
        StudentDTO student = studentService.getById(id);
        return student == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(student);
    }

    @GetMapping("/students")
    public List<StudentDTO> getStudents() {
        return studentService.getAll().collectList().block();
    }

    @PutMapping("/add_student")
    public ResponseEntity<StudentDTO> addCourse(@RequestBody Student student) {
        if(student == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(studentService.save(student));
    }
}