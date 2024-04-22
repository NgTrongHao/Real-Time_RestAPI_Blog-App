package com.nguyentronghao.realtime_restapi_blogapp.repository;

import com.nguyentronghao.realtime_restapi_blogapp.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
    Optional<Comment> findByPostIdAndId(Long postId, Long commentId);
}