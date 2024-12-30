package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GradesDTO {
    private int id;
    private int shadowId;
    private int grade;
}