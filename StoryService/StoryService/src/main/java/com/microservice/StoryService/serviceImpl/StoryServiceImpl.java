package com.microservice.StoryService.serviceImpl;

import com.microservice.StoryService.dto.StoryDto;
import com.microservice.StoryService.dto.UserDto;
import com.microservice.StoryService.entity.Story;
import com.microservice.StoryService.exceptions.CustomException;
import com.microservice.StoryService.feign.UserInterface;
import com.microservice.StoryService.repository.StoryRepository;
import com.microservice.StoryService.service.StoryService;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class StoryServiceImpl implements StoryService {
    @Autowired
    private UserInterface userInterface;
    @Autowired
    private StoryRepository storyRepository;
    @Override
    public StoryDto createStoryService(Story story, String token) throws CustomException {
        UserDto user=userInterface.getTokenUser(token);
        Story newStory=new Story();
        newStory.setDescription(story.getDescription());
        newStory.setImage(story.getImage());
        newStory.setUser(user.getId());
        Story storyExist=storyRepository.findStoryByUser(newStory.getUser());
        if(storyExist!=null){
            storyRepository.delete(newStory);
            throw new CustomException("Already you have one Story");
        }
        else{
            Story savedStory= storyRepository.save(newStory);
            user.setPassword(null);
            savedStory.setUser(user.getId());
            StoryDto storyDto=singleStoryDto(savedStory);
            return storyDto;
        }
    }
    @Override
    public List<StoryDto> getAllUserStories(String token) throws CustomException {
        UserDto user=userInterface.getTokenUser(token);
        List<UserDto>allFollowersStories=user.getFollowers().stream().map((f)->userInterface.getUserById(f)).toList();
        List<Story> allUsersStories = allFollowersStories.stream()
                .map(follower -> storyRepository.findStoryByUser(follower.getId()))
                .toList();
        List<StoryDto> allUsersStoriesDtos = allUsersStories.stream()
                .map(s -> {
                    if (s != null) {
                        return singleStoryDto(s);
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull) // Remove any null entries
                .toList();
        return allUsersStoriesDtos;
    }

    @Override
    public StoryDto getCurrentUserStoryService(String token){
        UserDto user=userInterface.getTokenUser(token);
        Story story= storyRepository.findStoryByUser(user.getId());
        if(story!=null){
            StoryDto storyDto=singleStoryDto(story);
            return storyDto;
        }
        else{
            throw new CustomException("there is no Story to this user");
        }
    }

    @Override
    public StoryDto updateCurrentUserStory(Story story,String token) throws CustomException {
        UserDto user=userInterface.getTokenUser(token);
        Story existingStory = storyRepository.findStoryByUser(user.getId());
        if (existingStory == null) {
            throw new CustomException("Story not found for the current user");
        }
        existingStory.setDescription(story.getDescription());
        existingStory.setImage(story.getImage());
        Story dbStory= storyRepository.save(existingStory);
        StoryDto storyDto=singleStoryDto(dbStory);
        return storyDto;
    }

    @Override
    public String deleteCurrentUserStoryService(String token) throws CustomException {
        UserDto user=userInterface.getTokenUser(token);
        Story existingStory=storyRepository.findStoryByUser(user.getId());
        if (existingStory==null){
            throw new CustomException("Story not found with the UserId");
        }
        storyRepository.delete(existingStory);
        return "Successfully Deleted the Story! ";
    }
    public StoryDto singleStoryDto(Story story){
        StoryDto storyDto=new StoryDto();
        try{
            storyDto.setId(story.getId());
            storyDto.setDescription(story.getDescription());
            storyDto.setImage(story.getImage());
            storyDto.setUser(userInterface.getUserById(story.getUser()));
            storyDto.setCreatedAt(story.getCreatedAt());
            return storyDto;
        }
        catch (CustomException e){
            throw new CustomException("There is No Story for him");
        }
    }
}
