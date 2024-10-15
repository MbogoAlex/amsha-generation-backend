package com.generation.amsha.user.services;

import com.generation.amsha.user.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserAccountServices {
    UserDto register(UserRegistrationDto userDto);

    UserLoginResponseDto login(String email, String token);
    UserDto updateUser(UserUpdateDto userDto);

    UserDto setUserProfilePic(Integer userId, String bucketName, MultipartFile file) throws IOException;

    String deleteProfilePic(Integer userId, String bucketName);
    UserDto getUserById(Integer userId);
    UserDto getUserByEmail(String email);
    UserDto getUserByPhoneNumber(String phoneNumber);
    List<UserDto> getAllUsers();
    UserDto archiveUser(Integer userId);

    UserDto changePassword(UserPasswordDto userPasswordDto) throws Exception;
}
