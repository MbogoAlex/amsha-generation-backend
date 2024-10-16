package com.generation.amsha.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogUpdatePayload {
    private Integer blogId;
    private String bucketName;
    private String title;
    private String paragraph;
    private List<String> tags;
}
