package com.microservice.MessageService.serviceImpl;
import com.microservice.MessageService.dto.MessageDto;
import com.microservice.MessageService.dto.PostDto;
import com.microservice.MessageService.dto.UserDto;
import com.microservice.MessageService.entity.Message;
import com.microservice.MessageService.exceptions.CustomException;
import com.microservice.MessageService.exceptions.EmptyMessageImageException;
import com.microservice.MessageService.feign.PostInterface;
import com.microservice.MessageService.feign.UserInterface;
import com.microservice.MessageService.repository.MessageRepository;
import com.microservice.MessageService.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private UserInterface userInterface;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private PostInterface postInterface;

    @Override
    public List<UserDto> getAllTokenFollowerObjects(String token, Pageable pageable) throws CustomException {
        UserDto user = userInterface.getTokenUser(token);
        Set<Long> allFollowersNo = user.getFollowers();
        Set<Long>allFollowingsNo=user.getFollowings();
        List<UserDto>allfollowingUsers=userInterface.getAllUsers((int)pageable.getOffset(),pageable.getPageSize());
        List<UserDto> allUsers = userInterface.getAllUsers((int) pageable.getOffset(), pageable.getPageSize());
        List<UserDto> followers = allUsers.stream()
                .filter(u -> allFollowersNo.contains(u.getId()))
                .toList();
        List<UserDto>followings=allfollowingUsers.stream().filter(u->allFollowingsNo.contains(u.getId())).toList();
        List<UserDto> all = new ArrayList<>(followers);
        all.addAll(followings);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), all.size());
        List<UserDto>allFollowersDtos= new PageImpl<>(all.subList(start, end), pageable, all.size()).getContent();
        return allFollowersDtos.stream().map((f)-> {
            UserDto userDto=userInterface.getUserById(f.getId());
            userDto.setPassword(null);
            return userDto;
        }).toList();
    }

    @Override
    public MessageDto sendMessageService(String message, MultipartFile image, Long receiverId, String token) throws EmptyMessageImageException, IOException {
        UserDto sendUser = userInterface.getTokenUser(token);
        UserDto receiveUser = userInterface.getUserById(receiverId);
        if ((sendUser.getId() > 0 && receiveUser.getId() > 0) && !sendUser.getId().equals(receiveUser.getId())) {
            Message newMessage = new Message();
            newMessage.setText(message);
            if (image != null) {
                newMessage.setImage(image.getBytes());
            }
            newMessage.setSendUser(sendUser.getId());
            newMessage.setReceiveUser(receiveUser.getId());
            if (newMessage.getText().isEmpty() && newMessage.getImage() == null) {
                throw new EmptyMessageImageException("Empty Message cant accept ");
            }
            Message dbMessage= messageRepository.save(newMessage);
            MessageDto messageDto=singleMessageDto(dbMessage);
            return messageDto;
        }
        throw new EmptyMessageImageException("Please send to the valid user");
    }

    @Override
    public ResponseEntity<?> getAllMessages(String token, Pageable pageable, Long receiverId) {
        UserDto user = userInterface.getTokenUser(token);
        List<Message> allTokenUserMessages = messageRepository.findAllMessagesBySendUserAndReceiveUser(user.getId(), receiverId, pageable);
        List<Message> allReceiverUserMessages = messageRepository.findAllMessagesByReceiveUserAndSendUser(user.getId(), receiverId, pageable);
        List<Message> allMessages = new ArrayList<>(allTokenUserMessages);
        allMessages.addAll(allReceiverUserMessages);
        List<MessageDto>allMessageDtos=allMessages.stream().map((m)->singleMessageDto(m)).toList();
        return !allMessageDtos.isEmpty() && pageable.getPageSize() != 0 ? ResponseEntity.ok(allMessageDtos) : ResponseEntity.ok("There is no Messages" + pageable.getPageNumber());
    }

    @Override
    public List<MessageDto> sendMessageToAllUsersService(String token, Long postId, List<Long > checkedUser) {
        UserDto tokenUser = userInterface.getTokenUser(token);
        PostDto post = postInterface.getPostById(token, postId);
        List<Message> messages = new ArrayList<>();
        for (Long userId : checkedUser) {
            UserDto user = userInterface.getUserById(userId);
            Message message = new Message();
            message.setText("");
            message.setReceiveUser(user.getId());
            message.setSendUser(tokenUser.getId());
            message.setImage(post.getImage());
            messages.add(message);
        }
        List<Message>allMessages= messageRepository.saveAll(messages);
        List<MessageDto>allMessageDtos=allMessages.stream().map((m)->singleMessageDto(m)).toList();
        return allMessageDtos;
    }
    public MessageDto singleMessageDto(Message dbMessage){
        MessageDto messageDto=new MessageDto();
        messageDto.setId(dbMessage.getId());
        messageDto.setText(dbMessage.getText());
        messageDto.setImage(dbMessage.getImage());
        messageDto.setSendUser(userInterface.getUserById(dbMessage.getSendUser()));
        messageDto.setReceiveUser(userInterface.getUserById(dbMessage.getReceiveUser()));
        return messageDto;
    }
}