package com.microservice.MessageService.service;

import com.microservice.MessageService.dto.MessageDto;
import com.microservice.MessageService.dto.UserDto;
import com.microservice.MessageService.entity.Message;
import com.microservice.MessageService.exceptions.CustomException;
import com.microservice.MessageService.exceptions.EmptyMessageImageException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MessageService {
    List<UserDto> getAllTokenFollowerObjects(String token, Pageable pageable) throws CustomException;
    MessageDto sendMessageService(String message, MultipartFile image, Long receiverId, String token) throws EmptyMessageImageException, IOException;
    List<MessageDto> sendMessageToAllUsersService(String token, Long postId, List<Long> checkedUser);
    ResponseEntity<?> getAllMessages(String token, Pageable pageable, Long receiverId);
}
