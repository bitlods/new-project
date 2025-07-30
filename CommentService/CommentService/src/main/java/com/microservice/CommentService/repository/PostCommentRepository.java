package com.microservice.CommentService.repository;

import com.microservice.CommentService.entity.PostComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment,Long> {
    List<PostComment> findCommentsByPost(Pageable pageable, Long postId);
}
