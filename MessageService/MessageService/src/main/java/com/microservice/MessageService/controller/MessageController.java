package com.microservice.MessageService.controller;

import com.microservice.MessageService.dto.MessageDto;
import com.microservice.MessageService.dto.SelectedUsers;
import com.microservice.MessageService.dto.UserDto;
import com.microservice.MessageService.exceptions.CustomException;
import com.microservice.MessageService.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
//@CrossOrigin({"http://localhost:5173/", "http://localhost:8005/"})
@RequestMapping("/main/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @GetMapping("/getfollowers")
    public List<UserDto>getAllFollowers(@RequestHeader("Authorization")String token,
                                        @RequestParam(defaultValue = "0")Integer page,
                                        @RequestParam(defaultValue = "1")Integer size) throws CustomException {
        PageRequest pageRequest=PageRequest.of(page,size);
        return messageService.getAllTokenFollowerObjects(token,pageRequest);
    }
    @PostMapping("/sendmessage")
    public ResponseEntity<?> sendMessage(@RequestParam("message") String message,
                                         @RequestParam(value = "image", required = false) MultipartFile image,
                                         @RequestParam("receiverId") Long receiverId,
                                         @RequestHeader("Authorization") String token) {
        try {
            MessageDto sentMessage = messageService.sendMessageService(message, image, receiverId, token);
            return ResponseEntity.ok(sentMessage);
        } catch (Exception e) {
            String errorMessage = "Failed to send message: " + e.getMessage();
            return ResponseEntity.badRequest().body(errorMessage); // Return an error response
        }
    }
    @GetMapping("/getsendermessage")
    public ResponseEntity<?>getAllUserMessages(@RequestHeader("Authorization")String token,
                                                   @RequestParam("receiverId")Long receiverId,
                                                       @RequestParam(defaultValue = "0")Integer page,
                                                       @RequestParam(defaultValue = "1")Integer size){
        try{
            PageRequest pageRequest= PageRequest.of(page,size);
            return messageService.getAllMessages(token,pageRequest,receiverId);
        }
        catch (CustomException e){
            String errorMessage="Failed to get Messages "+e.getMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }
    @PostMapping("/sendposttousers")
    public ResponseEntity<?>sendPostToAllUsers(@RequestHeader("Authorization")String token,
                                           @RequestBody SelectedUsers selectedUsers){
        return ResponseEntity.ok(messageService.sendMessageToAllUsersService(token,selectedUsers.getPostId(),selectedUsers.getCheckedUser()));
    }
}