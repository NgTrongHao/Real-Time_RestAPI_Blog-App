package com.nguyentronghao.realtime_restapi_blogapp.service.implement;

import com.nguyentronghao.realtime_restapi_blogapp.model.error.ResourceNotFoundException;
import com.nguyentronghao.realtime_restapi_blogapp.model.dto.comment.CommentDto;
import com.nguyentronghao.realtime_restapi_blogapp.model.entity.Comment;
import com.nguyentronghao.realtime_restapi_blogapp.model.entity.Post;
import com.nguyentronghao.realtime_restapi_blogapp.repository.CommentRepository;
import com.nguyentronghao.realtime_restapi_blogapp.repository.PostRepository;
import com.nguyentronghao.realtime_restapi_blogapp.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }
    
    /**
     * Adds a new comment to the specified post.
     *
     * @param postId     The ID of the post to which the comment will be added
     * @param commentDto The CommentDto containing the information of the new comment
     * @return The DTO representation of the newly added comment
     */
    @Override
    public CommentDto addComment(Long postId, CommentDto commentDto) {
        Comment comment = convertCommentDtoToComment(commentDto);
        comment.setPost(findPostByPostId(postId));
        return convertCommentToCommentDto(commentRepository.save(comment));
    }
    
    /**
     * Retrieves a list of comments for the specified post.
     *
     * @param postId The ID of the post for which to retrieve comments
     * @return A list of DTO representations of comments belonging to the specified post
     */
    @Override
    public List<CommentDto> getComments(Long postId) {
        return commentRepository.findByPostId(postId).stream()
                .map(this::convertCommentToCommentDto).toList();
    }
    
    /**
     * Retrieves a specific comment for the specified post.
     *
     * @param postId    The ID of the post to which the comment belongs
     * @param commentId The ID of the comment to retrieve
     * @return The DTO representation of the specified comment
     * @throws ResourceNotFoundException if the comment with the specified ID does not exist
     */
    @Override
    public CommentDto getComment(Long postId, Long commentId) {
        return convertCommentToCommentDto(findCommentByPostIdAndCommentId(postId, commentId));
    }
    
    /**
     * Updates a comment associated with the specified post.
     *
     * @param postId     The ID of the post to which the comment belongs
     * @param commentId  The ID of the comment to update
     * @param commentDto The DTO containing the updated comment information
     * @return The updated DTO representation of the comment
     * @throws ResourceNotFoundException if the comment with the specified ID does not exist in the specified post
     */
    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        Comment comment = convertCommentDtoToComment(commentDto);
        comment.setId(findCommentByPostIdAndCommentId(postId, commentId).getId());
        return convertCommentToCommentDto(commentRepository.save(comment));
    }
    
    /**
     * Deletes a comment associated with the specified post.
     *
     * @param postId    The ID of the post to which the comment belongs
     * @param commentId The ID of the comment to delete
     * @throws ResourceNotFoundException if the comment with the specified ID does not exist in the specified post
     */
    @Override
    public void deleteComment(Long postId, Long commentId) {
        commentRepository.delete(findCommentByPostIdAndCommentId(postId, commentId));
    }
    
    /**
     * Retrieves a post from the repository by its unique identifier.
     *
     * @param postId The unique identifier of the post
     * @return The retrieved post
     * @throws ResourceNotFoundException If the post with the specified ID is not found
     */
    private Post findPostByPostId(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId.toString()));
    }
    
    /**
     * Finds a comment by its ID and associated post ID.
     *
     * @param postId    The ID of the post to which the comment belongs
     * @param commentId The ID of the comment to find
     * @return The Comment entity if found, otherwise throws ResourceNotFoundException
     * @throws ResourceNotFoundException if the comment with the specified ID does not exist in the specified post
     */
    private Comment findCommentByPostIdAndCommentId(Long postId, Long commentId) {
        findPostByPostId(postId);
        return commentRepository.findByPostIdAndId(postId, commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + commentId, "postId ", postId.toString()));
    }
    
    /**
     * Convert a Comment object to a CommentDto object.
     *
     * @param comment The Comment object to be converted
     * @return The CommentDto object after conversion
     */
    private CommentDto convertCommentToCommentDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }
    
    /**
     * Convert a CommentDto object to a Comment object.
     *
     * @param commentDto The CommentDto object to be converted
     * @return The Comment object after conversion
     */
    private Comment convertCommentDtoToComment(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }
}
