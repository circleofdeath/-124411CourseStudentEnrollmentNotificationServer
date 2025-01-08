package com.example.demo.controller;

import com.example.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    public NotificationService notificationService;

    @GetMapping("/get_unread/{id}")
    public List<String> getUnread(@PathVariable long id) {
        return notificationService.getOnly(id);
    }

    @GetMapping("/read/{id}")
    public List<String> read(@PathVariable long id) {
        return notificationService.readNotifications(id);
    }

    @PostMapping("/send/{id}")
    public void send(@PathVariable long id, @RequestBody String message) {
        notificationService.sendNotification(id, String.valueOf(message));
    }

    @PostMapping("/broadcast/{id}")
    public void broadcast(@PathVariable long id, @RequestBody String message) {
        notificationService.broadcast(id, String.valueOf(message));
    }
}