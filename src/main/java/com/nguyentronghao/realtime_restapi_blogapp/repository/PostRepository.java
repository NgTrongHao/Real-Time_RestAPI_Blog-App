package com.nguyentronghao.realtime_restapi_blogapp.repository;

import com.nguyentronghao.realtime_restapi_blogapp.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategoryId(Long categoryId);
    
    @Query(
            "SELECT p FROM Post p WHERE " +
                    "p.content LIKE CONCAT('%', :keyword , '%') " +
                    "OR p.category.description LIKE CONCAT('%', :keyword ,'%') " +
                    "OR p.title LIKE CONCAT('%', :keyword, '%')"
    )
    List<Post> searchPosts(String keyword);
}