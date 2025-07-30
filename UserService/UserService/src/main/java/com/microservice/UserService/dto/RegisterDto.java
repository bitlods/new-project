package com.microservice.UserService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    private Long id;
    private String name;
    private String description;
    private Long phoneNumber;
    private String email;
    private String gender;
    private String password;
}
