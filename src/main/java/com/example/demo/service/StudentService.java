package com.example.demo.service;

import com.example.demo.dto.CourseDTO;
import com.example.demo.dto.StudentDTO;
import com.example.demo.entity.Course;
import com.example.demo.entity.CourseStudentRelationship;
import com.example.demo.entity.Student;
import com.example.demo.repo.CourseRepo;
import com.example.demo.repo.CourseStudentRelationshipRepo;
import com.example.demo.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class StudentService {
    @Autowired
    public CourseRepo courseRepo;
    @Autowired
    public StudentRepo studentRepo;
    @Autowired
    public CourseStudentRelationshipRepo manyToManyRepo;

    public Flux<StudentDTO> getAll() {
        return studentRepo.all().map(this::convert);
    }

    public StudentDTO getById(long id) {
        return convert(studentRepo.findById(id));
    }

    public StudentDTO save(Student course) {
        if(course.getName() != null) {
            if(course.getName().length() > 10) {
                course.setName(course.getName().substring(0, 10));
            }
        }

        return convert(studentRepo.save(course));
    }

    public StudentDTO save(StudentDTO dto) {
        return save(convert(dto));
    }

    public Student convert(StudentDTO dto) {
        if(dto == null) return null;
        Student student = new Student();
        student.setId(dto.getId());
        student.setName(dto.getName());
        student.setAge(dto.getAge());
        student.setUnread(dto.getUnread());

        if(dto.getCourseList() != null) {
            dto.getCourseList().forEach(course -> {
                courseRepo.save(course);

                if(manyToManyRepo.findByLink(course.getId(), student.getId()) == null) {
                    CourseStudentRelationship relationship = new CourseStudentRelationship();
                    relationship.setCourseID(course.getId());
                    relationship.setStudentID(student.getId());
                    manyToManyRepo.save(relationship);
                }
            });
        }

        return student;
    }

    public StudentDTO convert(Student raw) {
        if(raw == null) return null;
        StudentDTO dto = new StudentDTO();
        dto.setId(raw.getId());
        dto.setName(raw.getName());
        dto.setAge(raw.getAge());
        dto.setUnread(raw.getUnread());

        dto.setCourseList(manyToManyRepo
                .all()
                .filter(e -> e.getStudentID() == raw.getId())
                .map(e -> courseRepo.findById(e.getCourseID()))
                .collectList()
                .block()
        );

        return dto;
    }
}