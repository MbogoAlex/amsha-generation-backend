package com.generation.amsha.user.model;

import com.generation.amsha.blog.model.Blog;
import com.generation.amsha.blog.model.Comment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdate;
    private LocalDateTime archivedAt;
    private Role role;
    private Boolean archived;
    @OneToOne(mappedBy = "userAccount", optional = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserImage image;
    @OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Blog> blogArticles = new ArrayList<>();
    @OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> blogComments = new ArrayList<>();
}
