package com.microservice.NotificationService.feign;

import com.microservice.NotificationService.dto.PostDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("POST-SERVICE")
public interface PostInterface {
    @GetMapping("/main/posts/getpost")
    public PostDto getPostById(@RequestHeader("Authorization")String token, @RequestParam("postId")Long postId);
}
