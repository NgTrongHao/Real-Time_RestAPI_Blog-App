package com.nguyentronghao.realtime_restapi_blogapp.controller;

import com.nguyentronghao.realtime_restapi_blogapp.model.dto.comment.CommentDto;
import com.nguyentronghao.realtime_restapi_blogapp.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api", headers = "X-API-VERSION=1")
@Tag(
        name = "Rest APIs for Comment Resource",
        description = "CRUD Rest APIs for Comment Resource"
)
public class CommentController {
    private final CommentService commentService;
    
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    
    @GetMapping("/post/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Long postId) {
        return new ResponseEntity<>(commentService.getComments(postId), HttpStatus.OK);
    }
    
    @PostMapping("/post/{postId}/comment")
    @PreAuthorize("hasRole('ROLE_USER')")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    public ResponseEntity<CommentDto> addComment(@PathVariable Long postId, @RequestBody @Valid CommentDto commentDto) {
        return new ResponseEntity<>(commentService.addComment(postId, commentDto), HttpStatus.CREATED);
    }
    
    @GetMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDto> getComment(@PathVariable Long postId, @PathVariable Long commentId) {
        return new ResponseEntity<>(commentService.getComment(postId, commentId), HttpStatus.OK);
    }
    
    @PutMapping("/post/{postId}/comment/{commentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody @Valid CommentDto commentDto) {
        return new ResponseEntity<>(commentService.updateComment(postId, commentId, commentDto), HttpStatus.OK);
    }
    
    @DeleteMapping("/post/{postId}/comment/{commentId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    public ResponseEntity<?> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
