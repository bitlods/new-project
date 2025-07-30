package com.microservice.UserService.service;

import com.microservice.UserService.entity.User;
import com.microservice.UserService.exceptions.CustomException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User findUserById(Long userId);
    List<User> findAllUsers(Pageable pageable);
    User findUserByEmail(String email) throws CustomException;
    User followUserService(String token, Long userId2) throws CustomException;
    User updateUserService(User user,String token);
    List<User>findAllUsers();
    List<User> searchUser(String name);
    //    List<User>searchUser(String query);
    User findUserByToken(String email) throws CustomException;
    User editProfile(String token,String name, String description);
}
