package com.nguyentronghao.realtime_restapi_blogapp.model.dto.post;

import com.nguyentronghao.realtime_restapi_blogapp.model.dto.comment.CommentDto;
import com.nguyentronghao.realtime_restapi_blogapp.model.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link Post}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "PostDto model information"
)
public class PostDto implements Serializable {
    private Long id;
    
    @Schema(
            description = "Blog post title"
    )
    @NotEmpty
    @Size(min = 2, message = "Post title should be at least 2 characters long")
    private String title;
    
    @Schema(
            description = "Blog post content"
    )
    @NotEmpty
    @Size(min = 10, message = "Post content should be at least 10 characters long")
    private String content;
    
    @Schema(
            description = "Blog post author"
    )
    private String author;
    
    @Schema(
            description = "Blog post description"
    )
    @NotEmpty
    @Size(min = 10, message = "Post description should be at least 10 characters long")
    private String description;
    
    private Set<CommentDto> comments;
    
    @Schema(
            description = "Blog post category"
    )
    private Long categoryId;
}