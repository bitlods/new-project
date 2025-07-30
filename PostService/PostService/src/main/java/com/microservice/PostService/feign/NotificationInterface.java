package com.microservice.PostService.feign;

import com.microservice.PostService.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("NOTIFICATION-SERVICE")
public interface NotificationInterface {
    @PostMapping("/main/notifications/likenotification")
    public void likeNotification(@RequestHeader("Authorization")String token,
                                 @RequestParam("postId")Long postId) throws CustomException;
}
