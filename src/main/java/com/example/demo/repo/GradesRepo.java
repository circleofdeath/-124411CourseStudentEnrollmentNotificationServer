package com.example.demo.repo;

import com.example.demo.dto.GradesDTO;

import java.util.ArrayList;
import java.util.Optional;

// i'm not gonna setup JPA today
public class GradesRepo {
    private static final ArrayList<GradesDTO> grades = new ArrayList<>();

    public ArrayList<GradesDTO> all() {
        return grades;
    }

    public Optional<GradesDTO> getById(int id) {
        for(GradesDTO grade : grades) {
            if(grade.getShadowId() == id) {
                return Optional.of(grade);
            }
        }

        return Optional.empty();
    }

    public void save(GradesDTO grade) {
        if(grade == null) {
            return;
        }

        for(int i = 0; i < grades.size(); i++) {
            if(grades.get(i).getId() == grade.getId()) {
                grade.setId(i);
                grades.set(i, grade);
                return;
            }
        }

        grades.add(grade);
        grade.setId(grades.size() - 1);
    }
}