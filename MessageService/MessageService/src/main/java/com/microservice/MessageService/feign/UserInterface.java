package com.microservice.MessageService.feign;

import com.microservice.MessageService.dto.UserDto;
import com.microservice.MessageService.exceptions.CustomException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("USER-SERVICE")
public interface UserInterface {
    @GetMapping("/main/users/tokenUser")
    UserDto getTokenUser(@RequestHeader("Authorization")String token) throws CustomException;
    @GetMapping("/users/allUsers/userId")
    UserDto getUserById(@RequestParam("userId")Long userId);
    @GetMapping("/users/allUsers")
    List<UserDto> getAllUsers(@RequestParam(defaultValue = "0")Integer page, @RequestParam(defaultValue = "1")Integer size);
}
