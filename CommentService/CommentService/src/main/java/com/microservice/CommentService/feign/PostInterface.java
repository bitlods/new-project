package com.microservice.CommentService.feign;

import com.microservice.CommentService.dto.PostDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient("POST-SERVICE")
public interface PostInterface {
    @GetMapping("/main/posts/getpost")
    PostDto getPostById(@RequestHeader("Authorization")String token,
                               @RequestParam("postId")Long postId);
}
