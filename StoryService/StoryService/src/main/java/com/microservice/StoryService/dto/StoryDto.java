package com.microservice.StoryService.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryDto {
    private Long id;
    private String description;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[]image;
    private UserDto user;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
