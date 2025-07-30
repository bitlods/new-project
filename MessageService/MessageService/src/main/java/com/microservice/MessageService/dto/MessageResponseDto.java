package com.microservice.MessageService.dto;

import com.microservice.MessageService.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseDto {
    private List<Message>allMsgs;
    private String text;
}
