package com.example.demo.service;

import com.example.demo.dto.CourseDTO;
import com.example.demo.entity.Course;
import com.example.demo.entity.CourseStudentRelationship;
import com.example.demo.entity.Student;
import com.example.demo.repo.CourseRepo;
import com.example.demo.repo.CourseStudentRelationshipRepo;
import com.example.demo.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    public CourseRepo courseRepo;
    @Autowired
    public StudentRepo studentRepo;
    @Autowired
    public CourseStudentRelationshipRepo manyToManyRepo;

    public List<Student> getEnrollments(long courseId) {
        CourseDTO courseDTO = getById(courseId);
        if(courseDTO == null) return List.of();

        /*
        if(students.isEmpty()) {
            courseDTO.setStatus("Набір студентів ще не відкрито");
            save(courseDTO);
        }
         */

        return courseDTO.getStudentList();
    }

    public Flux<CourseDTO> getAll() {
        return courseRepo.all().map(this::convert);
    }

    public CourseDTO getById(long id) {
        return convert(courseRepo.findById(id));
    }

    public CourseDTO save(Course course) {
        if(course != null && course.getName() != null) {
            if(course.getName().length() > 20) {
                course.setName(course.getName().substring(0, 20));
            }
        }

        return convert(courseRepo.save(course));
    }

    public CourseDTO save(CourseDTO dto) {
        return save(convert(dto));
    }

    public Course convert(CourseDTO dto) {
        if(dto == null) return null;
        Course course = new Course();
        course.setId(dto.getId());
        course.setName(dto.getName());
        course.setDescription(dto.getDescription());
        course.setStatus(dto.getStatus());

        if(dto.getStudentList() != null) {
            dto.getStudentList().forEach(student -> {
                studentRepo.save(student);

                if(manyToManyRepo.findByLink(course.getId(), student.getId()) == null) {
                    CourseStudentRelationship relationship = new CourseStudentRelationship();
                    relationship.setCourseID(course.getId());
                    relationship.setStudentID(student.getId());
                    manyToManyRepo.save(relationship);
                }
            });
        }

        return course;
    }

    public CourseDTO convert(Course raw) {
        if(raw == null) return null;
        CourseDTO dto = new CourseDTO();
        dto.setId(raw.getId());
        dto.setName(raw.getName());
        dto.setStatus(raw.getStatus());
        dto.setDescription(raw.getDescription());

        dto.setStudentList(manyToManyRepo
                .all()
                .filter(e -> e.getCourseID() == raw.getId())
                .map(e -> studentRepo.findById(e.getStudentID()))
                .collectList()
                .block()
        );

        return dto;
    }
}