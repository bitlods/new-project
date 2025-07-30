package com.microservice.MessageService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[]image;
    private Long sendUser;
    private Long receiveUser;
}
