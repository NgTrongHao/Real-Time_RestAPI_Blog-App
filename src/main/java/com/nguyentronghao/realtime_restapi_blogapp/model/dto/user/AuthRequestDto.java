package com.nguyentronghao.realtime_restapi_blogapp.model.dto.user;

import com.nguyentronghao.realtime_restapi_blogapp.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link User}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "User authentication model information"
)
public class AuthRequestDto {
    @Schema(description = "User's username or email")
    @NotBlank
    private String usernameOrEmail;
    
    @Schema(description = "User's password")
    @NotBlank
    private String password;
}
