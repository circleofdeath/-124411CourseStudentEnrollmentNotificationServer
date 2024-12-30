package com.example.demo.service;

import com.example.demo.repo.GradesRepo;
import com.example.demo.dto.GradesDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class GradesService {
    GradesRepo repo = new GradesRepo();

    public void save(int id) {
        repo.save(new GradesDTO(-1, id, new Random().nextInt(12)));
    }

    public Optional<GradesDTO> getById(int id) {
        return repo.getById(id);
    }

    public GradesDTO[] getRaw() {
        return repo.all().toArray(GradesDTO[]::new);
    }
}