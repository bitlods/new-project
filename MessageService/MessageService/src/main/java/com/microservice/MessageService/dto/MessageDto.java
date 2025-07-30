package com.microservice.MessageService.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private Long id;
    private String text;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[]image;
    private UserDto sendUser;
    private UserDto receiveUser;
}
