package com.microservice.CommentService.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String description;
    private Long phoneNumber;
    private String email;
    private String gender;
    private String password;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[]profile;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[]background;
    private Set<Long> followers=new HashSet<>();
    private Set<Long>followings=new HashSet<>();
}
