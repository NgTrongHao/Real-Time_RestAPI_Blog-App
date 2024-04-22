package com.nguyentronghao.realtime_restapi_blogapp.service;

import com.nguyentronghao.realtime_restapi_blogapp.model.dto.post.PostDto;
import com.nguyentronghao.realtime_restapi_blogapp.model.dto.post.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getPageOfPosts(int pageNum, int pageSize, String pageOrderBy, String direction);
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto, Long id);
    void deletePost(Long id);
    List<PostDto> getAllPostsByCategoryId(Long categoryId);
    List<PostDto> searchPosts(String keyword);
}
