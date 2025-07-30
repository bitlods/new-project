package com.microservice.NotificationService.controller;

import com.microservice.NotificationService.dto.NotificationDto;
import com.microservice.NotificationService.entity.Notification;
import com.microservice.NotificationService.exceptions.CustomException;
import com.microservice.NotificationService.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin({"http://localhost:5173/", "http://localhost:8005/"})
@RequestMapping("/main/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @PostMapping("/likenotification")
    public void likeNotification(@RequestHeader("Authorization")String token,
                                         @RequestParam("postId")Long postId) throws CustomException {
        notificationService.likeNotificationService(token,postId);
    }
    @GetMapping("/allnotifications")
    public List<NotificationDto>allNotification(@RequestHeader("Authorization")String token) throws CustomException {
        return notificationService.getAllNotifications(token);

    }
}
