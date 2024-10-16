package com.generation.amsha.blog.dto;

import com.generation.amsha.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogDto {
    private Integer id;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDto user;
    private String imagePath;
    private String paragraph;
    private List<String> tags;
    private List<CommentDto> comments;
}
