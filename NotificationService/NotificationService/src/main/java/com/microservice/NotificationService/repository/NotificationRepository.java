package com.microservice.NotificationService.repository;

import com.microservice.NotificationService.entity.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    boolean existsByPostIdAndUserId(Long postId,Long userId);
    void deleteByPostIdAndUserId(Long postId,Long userId);
}
