package com.nguyentronghao.realtime_restapi_blogapp.controller;

import com.nguyentronghao.realtime_restapi_blogapp.model.dto.post.PostDto;
import com.nguyentronghao.realtime_restapi_blogapp.model.dto.post.PostResponse;
import com.nguyentronghao.realtime_restapi_blogapp.service.PostService;
import com.nguyentronghao.realtime_restapi_blogapp.utils.ApplicationConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/post", headers = "X-API-VERSION=1")
@Tag(
        name = "Rest APIs for Post Resource",
        description = "CRUD Rest APIs for Post Resource"
)
public class PostController {
    
    private final PostService postService;
    
    
    public PostController(PostService postService) {
        this.postService = postService;
    }
    
    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNum", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNum,
            @RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "orderBy", defaultValue = ApplicationConstants.DEFAULT_PAGE_ORDER_BY, required = false) String pageOrderBy,
            @RequestParam(value = "direction", defaultValue = ApplicationConstants.DEFAULT_ORDER_DIRECTION, required = false) String direction
    ) {
        return new ResponseEntity<>(postService.getPageOfPosts(pageNum, pageSize, pageOrderBy, direction), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    @Operation(
            summary = "Get a specified post",
            description = "Allows to get a specified post by postId.",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Http Status 200 OK - Successfully operation"
                    )
            }
    )
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Create a new post",
            description = "Allows authorized users with the 'ROLE_ADMIN' role to create a new post.",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {
                    @ApiResponse(
                            responseCode = "201", description = "Http Status 201 Created - Successfully operation"
                    )
            }
    )
    public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Update a specified post",
            description = "Allows authorized users with the 'ROLE_ADMIN' role to update a specified post by postId.",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Http Status 200 OK - Successfully operation"
                    )
            }
    )
    public ResponseEntity<PostDto> updatePost(@RequestBody @Valid PostDto postDto, @PathVariable Long id) {
        return new ResponseEntity<>(postService.updatePost(postDto, id), HttpStatus.OK);
    }
    
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Delete a specified post",
            description = "Allows authorized users with the 'ROLE_ADMIN' role to delete a specified post by postId.",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Http Status 200 OK - Successfully operation"
                    )
            }
    )
    public ResponseEntity<PostDto> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/category/{categoryId}")
    @Operation(
            summary = "Get list of post by category id",
            description = "Allows users to get list of post by category id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Http Status 200 OK - Successfully operation"
                    )
            }
    )
    public ResponseEntity<List<PostDto>> getAllPostsByCategoryId(@PathVariable Long categoryId) {
        return new ResponseEntity<>(postService.getAllPostsByCategoryId(categoryId), HttpStatus.OK);
    }
    
    @GetMapping("/search")
    @Operation(
            summary = "Get list of post by search keyword",
            description = "Allows users to get list of post by search keyword.",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Http Status 200 OK - Successfully operation"
                    )
            }
    )
    public ResponseEntity<List<PostDto>> getAllPostsBySearch(@RequestParam(value = "query") String keyword) {
        return new ResponseEntity<>(postService.searchPosts(keyword), HttpStatus.OK);
    }
}
