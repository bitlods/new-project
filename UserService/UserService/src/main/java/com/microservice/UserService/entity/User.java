package com.microservice.UserService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ElementCollection
    private Set<Long> followers=new HashSet<>();
    @ElementCollection
    private Set<Long>followings=new HashSet<>();
}
