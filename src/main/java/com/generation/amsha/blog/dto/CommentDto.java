package com.generation.amsha.blog.dto;

import com.generation.amsha.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Integer id;
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDto user;
    private Integer blogId;
    private Boolean archived;
}
