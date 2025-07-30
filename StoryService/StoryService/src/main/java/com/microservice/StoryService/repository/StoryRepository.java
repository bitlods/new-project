package com.microservice.StoryService.repository;

import com.microservice.StoryService.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story,Long> {
//    @Query("from Story s where s.user!=:id")
      Story findStoryByUser(Long userId);
//    Story findByUserId(Integer id);
}
