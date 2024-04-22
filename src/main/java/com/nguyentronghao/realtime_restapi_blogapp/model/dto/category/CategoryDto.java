package com.nguyentronghao.realtime_restapi_blogapp.model.dto.category;

import com.nguyentronghao.realtime_restapi_blogapp.model.entity.Category;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link Category}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto implements Serializable {
    private Long id;
    
    @NotEmpty
    String name;
    String description;
}