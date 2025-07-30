package com.microservice.CommentService.feign;

import com.microservice.CommentService.dto.UserDto;
import com.microservice.CommentService.exceptions.CustomException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("USER-SERVICE")
public interface UserInterface {
    @GetMapping("/main/users/tokenUser")
    UserDto getTokenUser(@RequestHeader("Authorization")String token) throws CustomException;
    @GetMapping("/users/allUsers/userId")
    UserDto getUserById(@RequestParam("userId")Long userId);
}
