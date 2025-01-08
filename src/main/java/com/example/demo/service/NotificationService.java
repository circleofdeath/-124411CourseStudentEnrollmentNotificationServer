package com.example.demo.service;

import com.example.demo.dto.CourseDTO;
import com.example.demo.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    public CourseService courseService;
    @Autowired
    public StudentService studentService;

    public List<String> getOnly(long studentId) {
        StudentDTO dto = studentService.getById(studentId);
        if(dto == null) return List.of();
        var unread = dto.getUnread();
        if(unread == null) {
            unread = List.of();
        }
        return unread;
    }

    public List<String> readNotifications(long studentId) {
        StudentDTO dto = studentService.getById(studentId);
        if(dto == null) return List.of();
        var unread = dto.getUnread();
        if(unread == null) {
            unread = List.of();
        }
        dto.setUnread(List.of());
        studentService.save(dto);
        return unread;
    }

    public void sendNotification(long studentId, String message) {
        StudentDTO dto = studentService.getById(studentId);
        if(dto != null) {
            if(dto.getUnread() == null) {
                dto.setUnread(new ArrayList<>());
            }

            dto.getUnread().add(message);
            studentService.save(dto);
        }
    }

    public void broadcast(long courseId, String message) {
        CourseDTO course = courseService.getById(courseId);
        if(course != null && course.getStudentList() != null) {
            course.getStudentList().forEach(student -> sendNotification(student.getId(), message));
        }
    }
}