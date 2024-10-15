package com.generation.amsha.user.mapper;

import com.generation.amsha.blog.model.Blog;
import com.generation.amsha.blog.model.Comment;
import com.generation.amsha.transactions.model.Transaction;
import com.generation.amsha.user.dto.UserDto;
import com.generation.amsha.user.dto.UserLoginResponseDto;
import com.generation.amsha.user.model.UserAccount;

import java.util.ArrayList;
import java.util.List;

public class UserAccountMapper {
    public UserDto toUserDto(UserAccount userAccount) {
        List<Blog> blogs;
        List<Comment> comments;
        List<Transaction> transactions;
        if(userAccount.getBlogArticles() == null) {
            blogs = new ArrayList<>();
        } else {
            blogs = userAccount.getBlogArticles();
        }

        if(userAccount.getBlogComments() == null) {
            comments = new ArrayList<>();
        } else {
            comments = userAccount.getBlogComments();
        }

        if(userAccount.getTransactions() == null) {
            transactions = new ArrayList<>();
        } else {
            transactions = userAccount.getTransactions();
        }

        return UserDto.builder()
                .id(userAccount.getId())
                .fullName(userAccount.getFullName())
                .email(userAccount.getEmail())
                .phoneNumber(userAccount.getPhoneNumber())
                .profilePic(userAccount.getUserProfilePic())
                .role(userAccount.getRole())
                .createdAt(userAccount.getCreatedAt())
                .archived(userAccount.getArchived())
                .archivedAt(userAccount.getArchivedAt())
                .blogs(blogs.size())
                .comments(comments.size())
                .transactions(transactions.size())
                .build();

    }

    public UserLoginResponseDto toUserLoginResponseDto(UserAccount userAccount, String token) {
        return UserLoginResponseDto.builder()
                .id(userAccount.getId())
                .fullName(userAccount.getFullName())
                .email(userAccount.getEmail())
                .phoneNumber(userAccount.getPhoneNumber())
                .createdAt(userAccount.getCreatedAt())
                .token(token)
                .archivedAt(userAccount.getArchivedAt())
                .build();

    }
}
