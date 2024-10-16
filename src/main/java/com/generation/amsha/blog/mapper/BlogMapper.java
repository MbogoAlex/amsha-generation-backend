package com.generation.amsha.blog.mapper;

import com.generation.amsha.blog.dto.BlogDto;
import com.generation.amsha.blog.dto.CommentDto;
import com.generation.amsha.blog.model.Blog;
import com.generation.amsha.blog.model.Comment;
import com.generation.amsha.user.mapper.UserAccountMapper;

import java.util.ArrayList;
import java.util.List;

public class BlogMapper {
    UserAccountMapper userAccountMapper = new UserAccountMapper();
    public BlogDto blogToBlogDto(Blog blog) {
        List<CommentDto> comments = new ArrayList<>();

        for(Comment comment : blog.getComments()) {
            comments.add(commentToCommentDto(comment));
        }
        return BlogDto.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .imagePath(blog.getImagePath())
                .paragraph(blog.getParagraph())
                .createdAt(blog.getCreatedAt())
                .updatedAt(blog.getUpdatedAt())
                .user(userAccountMapper.toUserDto(blog.getUserAccount()))
                .tags(blog.getTags())
                .comments(comments)
                .build();
    }

    public CommentDto commentToCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .body(comment.getBody())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .user(userAccountMapper.toUserDto(comment.getUserAccount()))
                .blogId(comment.getBlog().getId())
                .archived(comment.getArchived())
                .build();
    }
}
