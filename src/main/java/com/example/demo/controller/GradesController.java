package com.example.demo.controller;

import com.example.demo.service.GradesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.GradesDTO;

@RestController
@RequestMapping("/grades")
public class GradesController {
    @Autowired
    public GradesService grades;

    @GetMapping("/get_by_id/{id}")
    public int getGrade(@PathVariable int id) {
        return grades.getById(id).map(grade -> grade.getGrade()).orElse(-1);
    }

    @GetMapping("/append/{id}")
    public void append(@PathVariable int id) {
        grades.save(id);
    }

    @GetMapping("/raw")
    public GradesDTO[] getRaw() {
        return grades.getRaw();
    }
}