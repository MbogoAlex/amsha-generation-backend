package com.generation.amsha.user.model;

import com.generation.amsha.blog.model.Blog;
import com.generation.amsha.blog.model.Comment;
import com.generation.amsha.budget.model.Budget;
import com.generation.amsha.transactions.model.Transaction;
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
    private String userProfilePic;
    private String imageName;
    private Boolean subscribed;
    @Column(length = 500)
    private String lastPaymentToken;
    private String lastMerchantReference;
    private Boolean archived;
    @OneToOne(mappedBy = "userAccount", optional = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserImage image;
    @OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Blog> blogArticles = new ArrayList<>();
    @OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> blogComments = new ArrayList<>();
    @OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();
    @OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Budget> budgets = new ArrayList<>();
}
