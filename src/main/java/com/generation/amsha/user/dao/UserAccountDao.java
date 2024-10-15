package com.generation.amsha.user.dao;

import com.generation.amsha.user.model.UserAccount;

import java.util.List;

public interface UserAccountDao {
    UserAccount register(UserAccount userAccount);
    UserAccount updateUser(UserAccount userAccount);
    UserAccount getUserById(Integer userId);
    UserAccount getUserByEmail(String email);

    UserAccount getUserByMerchantReferenceId(String ref);
    UserAccount getUserByPhoneNumber(String phoneNumber);

    List<UserAccount> getAllUsers();
    UserAccount archiveUser(UserAccount userAccount);
    Boolean existsByPhoneNumber(String phoneNumber);
}
