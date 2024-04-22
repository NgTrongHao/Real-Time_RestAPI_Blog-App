package com.nguyentronghao.realtime_restapi_blogapp.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private List<PostDto> postList;
    private int pageNum;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean hasNext;
}
