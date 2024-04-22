package com.nguyentronghao.realtime_restapi_blogapp.service;

import com.nguyentronghao.realtime_restapi_blogapp.model.dto.comment.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto addComment(Long postId, CommentDto commentDto);
    List<CommentDto> getComments(Long postId);
    CommentDto getComment(Long postId, Long commentId);
    CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto);
    void deleteComment(Long postId, Long commentId);
}
