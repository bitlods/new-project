package com.microservice.UserService.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDetails {
    private String email;
    private String password;
}
