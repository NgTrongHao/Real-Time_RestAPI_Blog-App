package com.nguyentronghao.realtime_restapi_blogapp.model.dto.user;

import com.nguyentronghao.realtime_restapi_blogapp.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User model information for registration")
public class RegisterDto implements Serializable {
    @Schema(description = "User's name")
    @Size(min = 2, max = 50) @NotEmpty
    String name;
    
    @Schema(description = "User's username")
    @Size(min = 5, max = 20) @NotBlank
    String username;
    
    @Schema(description = "User's password")
    @Size(min = 6) @NotBlank
    String password;
    
    @Schema(description = "User's email")
    @Email @NotBlank
    String email;
}