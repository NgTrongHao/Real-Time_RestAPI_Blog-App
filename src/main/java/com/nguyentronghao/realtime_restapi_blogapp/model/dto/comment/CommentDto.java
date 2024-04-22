package com.nguyentronghao.realtime_restapi_blogapp.model.dto.comment;

import com.nguyentronghao.realtime_restapi_blogapp.model.entity.Comment;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link Comment}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto implements Serializable {
    private Long id;
    
    @NotEmpty(message = "Name should not be empty")
    String author;
    
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    String email;
    
    @NotEmpty(message = "Content should not be empty")
    @Size(min = 10, message = "Content should have at least 10 characters long")
    String content;
}