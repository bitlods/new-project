package com.microservice.NotificationService.service;

import com.microservice.NotificationService.dto.NotificationDto;
import com.microservice.NotificationService.entity.Notification;
import com.microservice.NotificationService.exceptions.CustomException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {
    void likeNotificationService(String token, Long postId) throws CustomException;
    List<NotificationDto> getAllNotifications(String token) throws CustomException;
}
