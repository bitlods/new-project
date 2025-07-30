package com.microservice.StoryService.controller;

import com.microservice.StoryService.dto.StoryDto;
import com.microservice.StoryService.entity.Story;
import com.microservice.StoryService.exceptions.CustomException;
import com.microservice.StoryService.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
//@CrossOrigin({"http://localhost:5173/", "http://localhost:8005/"})
@RequestMapping("/main/story")
public class StoryController {
    @Autowired
    private StoryService storyService;
    @PostMapping("/allStory")
    public ResponseEntity<?> createStory(@RequestParam("description")String description,
                                      @RequestParam("storyImage")MultipartFile storyImage,
                                      @RequestHeader("Authorization")String token) throws CustomException, IOException {
        Story story=new Story();
        story.setDescription(description);
        story.setImage(storyImage.getBytes());
        try {
            StoryDto storyDto=storyService.createStoryService(story,token);
            return ResponseEntity.ok(storyDto);
        }
        catch (CustomException e){
            return ResponseEntity.ok(e.getMessage());
        }

    }
    @GetMapping("/allStory")
    public ResponseEntity<?> getAllStories(@RequestHeader("Authorization")String token)throws CustomException{
        try {
            List<StoryDto> allUserStories = storyService.getAllUserStories(token);
            return ResponseEntity.ok(allUserStories);
        }
        catch (CustomException e){
            throw new  CustomException(e.getMessage());
        }
    }
    @GetMapping("/currentStory")
    public ResponseEntity<?> getCurrentUserStory(@RequestHeader("Authorization")String token){
        StoryDto storyDto= storyService.getCurrentUserStoryService(token);
        try {
            return ResponseEntity.ok(storyDto);
        }
        catch (CustomException e){
            return ResponseEntity.ok(e.getMessage());
        }
    }
    @PutMapping("/updateStory")
    public StoryDto updateCurrentUserStory(@RequestParam("description")String description,
            @RequestParam("storyImage")MultipartFile storyImage,
            @RequestHeader("Authorization")String token) throws CustomException, IOException {
        Story story=new Story();
        story.setDescription(description);
        story.setImage(storyImage.getBytes());
        StoryDto storyy =storyService.updateCurrentUserStory(story,token);
        return storyy;
    }
    @DeleteMapping("/deleteStory")
    public String deleteCurrentUserStory(@RequestHeader("Authorization")String token) throws CustomException {
        return storyService.deleteCurrentUserStoryService(token);
    }
}
