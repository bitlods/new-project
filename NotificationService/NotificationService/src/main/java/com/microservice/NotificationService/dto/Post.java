package com.microservice.NotificationService.dto;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Long id;
    private String description;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;
    private Long userId; // Store the user ID instead of the User entity
    @ElementCollection
    private List<Long> likes=new ArrayList<>();
}
