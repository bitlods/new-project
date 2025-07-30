package com.microservice.SavedPostService.repository;

import com.microservice.SavedPostService.entity.SavedPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavedPostRepository extends JpaRepository<SavedPost,Long> {
    Page<SavedPost> findPostByUser(Long userId, Pageable pageable);
    SavedPost findPostByPostAndUser(Long id, Long userId);
    List<SavedPost> findAllSavedPostsByUser(Long id);
}
