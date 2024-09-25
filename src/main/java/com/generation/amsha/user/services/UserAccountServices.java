package com.generation.amsha.user.services;

import com.generation.amsha.user.dto.UserDto;
import com.generation.amsha.user.dto.UserLoginDto;
import com.generation.amsha.user.dto.UserRegistrationDto;
import com.generation.amsha.user.dto.UserUpdateDto;

import java.util.List;

public interface UserAccountServices {
    UserDto register(UserRegistrationDto userDto);
    UserDto updateUser(UserUpdateDto userDto);
    UserDto getUserById(Integer userId);
    UserDto getUserByEmail(String email);
    UserDto getUserByPhoneNumber(String phoneNumber);
    List<UserDto> getAllUsers();
    UserDto archiveUser(Integer userId);
}
