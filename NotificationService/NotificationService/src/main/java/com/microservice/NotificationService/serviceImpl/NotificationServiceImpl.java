package com.microservice.NotificationService.serviceImpl;

import com.microservice.NotificationService.dto.NotificationDto;
import com.microservice.NotificationService.dto.PostDto;
import com.microservice.NotificationService.dto.UserDto;
import com.microservice.NotificationService.entity.Notification;
import com.microservice.NotificationService.exceptions.CustomException;
import com.microservice.NotificationService.feign.PostInterface;
import com.microservice.NotificationService.feign.UserInterface;
import com.microservice.NotificationService.repository.NotificationRepository;
import com.microservice.NotificationService.service.NotificationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private UserInterface userInterface;
    @Autowired
    private PostInterface postInterface;
    @Autowired
    private NotificationRepository notificationRepository;
    @Override
    @Transactional
    public void likeNotificationService(String token, Long postId) throws CustomException {
        UserDto user=userInterface.getTokenUser(token);
        PostDto post= postInterface.getPostById(token, postId);
        List<Notification>allNotifications=notificationRepository.findAll();
        if(user!=null&&post!=null&&!post.getUser().getId().equals(user.getId())){
            boolean postExistsInNotifications = notificationRepository.existsByPostIdAndUserId(postId,user.getId());
            Notification notification = new Notification();
            if (postExistsInNotifications) {
                notificationRepository.deleteByPostIdAndUserId(postId,user.getId());
            }
            if (!postExistsInNotifications) {
                notification.setPostId(post.getId());
                notification.setUserId(user.getId());
                Notification Notification= notificationRepository.save(notification);
                System.out.println("Notification "+Notification);
            }
        }
    }

    @Override
    public List<NotificationDto> getAllNotifications(String token) throws CustomException {
        UserDto user=userInterface.getTokenUser(token);
        List<Notification>allNotifications=notificationRepository.findAll();
        System.out.println("allNotifications "+allNotifications.size());;
        List<NotificationDto> allNotificationDtos=allNotifications.stream().map((n)->{
            UserDto userDto=userInterface.getUserById(n.getUserId());
            PostDto postDto=postInterface.getPostById(token,n.getPostId());
            NotificationDto notificationDto=new NotificationDto();
            notificationDto.setId(n.getId());
            notificationDto.setUser(userDto);
            notificationDto.setPost(postDto);
            return notificationDto;
        }).toList();
        System.out.println("dto "+allNotificationDtos.size());
        List<NotificationDto> allNotificationsOfPostUser = allNotificationDtos.stream()
                .filter(item -> item.getPost().getUser().getId()==user.getId())
                .toList();
        System.out.println("allNotificationsOdPostUser "+allNotificationsOfPostUser.size());
        return allNotificationsOfPostUser;
    }
}
