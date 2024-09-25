package com.generation.amsha.user.mapper;

import com.generation.amsha.user.dto.UserDto;
import com.generation.amsha.user.model.UserAccount;

public class UserAccountMapper {
    public UserDto toUserDto(UserAccount userAccount) {
        return UserDto.builder()
                .id(userAccount.getId())
                .fullName(userAccount.getFullName())
                .email(userAccount.getEmail())
                .phoneNumber(userAccount.getPhoneNumber())
                .createdAt(userAccount.getCreatedAt())
                .archived(userAccount.getArchived())
                .archivedAt(userAccount.getArchivedAt())
                .blogs(userAccount.getBlogArticles().size())
                .comments(userAccount.getBlogComments().size())
                .build();

    }
}
