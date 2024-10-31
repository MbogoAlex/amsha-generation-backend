package com.generation.amsha.user.controller;

import com.generation.amsha.response.Response;
import com.generation.amsha.user.dto.UserDto;
import com.generation.amsha.user.dto.UserLoginResponseDto;
import com.generation.amsha.user.dto.UserPasswordDto;
import com.generation.amsha.user.dto.UserUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserAccountController {
    ResponseEntity<Response> updateUser(UserUpdateDto userDto);

    ResponseEntity<Response> setProfilePic(Integer userId, String bucketName, MultipartFile file) throws IOException;
    ResponseEntity<Response> deleteProfilePic(Integer userId, String bucketName);
    ResponseEntity<Response> getUserById(Integer userId);
    ResponseEntity<Response> getUserByEmail(String email);
    ResponseEntity<Response> getUserByPhoneNumber(String phoneNumber);
    ResponseEntity<Response> getAllUsers();
    ResponseEntity<Response> archiveUser(Integer userId);
    ResponseEntity<Response> changePassword(UserPasswordDto userPasswordDto) throws Exception;
    ResponseEntity<Response> getUserWallet(Integer userId);
}
