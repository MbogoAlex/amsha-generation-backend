package com.generation.amsha.user.services;

import com.generation.amsha.user.dao.UserAccountDao;
import com.generation.amsha.user.dto.UserDto;
import com.generation.amsha.user.dto.UserRegistrationDto;
import com.generation.amsha.user.dto.UserUpdateDto;
import com.generation.amsha.user.mapper.UserAccountMapper;
import com.generation.amsha.user.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserAccountServicesImpl implements UserAccountServices{
    UserAccountMapper userAccountMapper = new UserAccountMapper();
    private final UserAccountDao userAccountDao;
    @Autowired
    public UserAccountServicesImpl(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }
    @Override
    public UserDto register(UserRegistrationDto userDto) {
        UserAccount userAccount = UserAccount.builder()
                .fullName(userDto.getFullName())
                .phoneNumber(userDto.getPhoneNumber())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .archived(false)
                .createdAt(LocalDateTime.now())
                .build();
        return userAccountMapper.toUserDto(userAccountDao.register(userAccount));
    }

    @Override
    public UserDto updateUser(UserUpdateDto userDto) {
        UserAccount userAccount = userAccountDao.getUserById(userDto.getId());
        userAccount.setFullName(userDto.getFullName());
        userAccount.setPhoneNumber(userDto.getPhoneNumber());
        userAccount.setEmail(userDto.getEmail());
        userAccount.setPassword(userDto.getPassword());
        userAccount.setRole(userDto.getRole());
        userAccount.setLastUpdate(LocalDateTime.now());
        return userAccountMapper.toUserDto(userAccountDao.updateUser(userAccount));
    }

    @Override
    public UserDto getUserById(Integer userId) {
        return userAccountMapper.toUserDto(userAccountDao.getUserById(userId));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return userAccountMapper.toUserDto(userAccountDao.getUserByEmail(email));
    }

    @Override
    public UserDto getUserByPhoneNumber(String phoneNumber) {
        return userAccountMapper.toUserDto(userAccountDao.getUserByPhoneNumber(phoneNumber));
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtos = new ArrayList<>();
        List<UserAccount> userAccounts = userAccountDao.getAllUsers();
        for(UserAccount userAccount : userAccounts) {
            userDtos.add(userAccountMapper.toUserDto(userAccount));
        }
        return userDtos;
    }

    @Override
    public UserDto archiveUser(Integer userId) {
        UserAccount userAccount = userAccountDao.getUserById(userId);
        userAccount.setArchived(true);
        userAccountDao.archiveUser(userAccount);
        return userAccountMapper.toUserDto(userAccount);
    }
}
