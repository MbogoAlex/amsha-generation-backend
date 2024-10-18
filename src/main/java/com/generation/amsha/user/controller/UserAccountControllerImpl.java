package com.generation.amsha.user.controller;

import com.generation.amsha.response.BuildResponse;
import com.generation.amsha.response.Response;
import com.generation.amsha.user.dto.UserPasswordDto;
import com.generation.amsha.user.dto.UserUpdateDto;
import com.generation.amsha.user.services.UserAccountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@CrossOrigin(origins = {"http://localhost:4200", "https://amsha-gen-96609f863a46.herokuapp.com"})
@RestController
@RequestMapping("/api/")
public class UserAccountControllerImpl implements UserAccountController {
    private BuildResponse buildResponse = new BuildResponse();
    private final UserAccountServices userAccountServices;
    @Autowired
    public UserAccountControllerImpl(
            UserAccountServices userAccountServices
    ) {
        this.userAccountServices = userAccountServices;
    }

    @Override
    @PutMapping("user")
    public ResponseEntity<Response> updateUser(UserUpdateDto userDto) {
        return buildResponse.buildResponse("user", userAccountServices.updateUser(userDto), "User updated", HttpStatus.OK);
    }
    @PutMapping("user/profilepic/{userId}/{bucketName}")
    @Override
    public ResponseEntity<Response> setProfilePic(@PathVariable("userId") Integer userId, @PathVariable("bucketName") String bucketName, MultipartFile file) throws IOException {
        return buildResponse.buildResponse("user", userAccountServices.setUserProfilePic(userId, bucketName, file), "User profile pic set", HttpStatus.OK);
    }
    @DeleteMapping("user/profilepic/{userId}/{bucketName}")
    @Override
    public ResponseEntity<Response> deleteProfilePic(@PathVariable("userId") Integer userId, @PathVariable("bucketName") String bucketName) {
        return buildResponse.buildResponse("user", userAccountServices.deleteProfilePic(userId, bucketName), "Profile pic deleted", HttpStatus.OK);
    }

    @Override
    @GetMapping("user/id/{userId}")
    public ResponseEntity<Response> getUserById(@PathVariable("userId") Integer userId) {
        return buildResponse.buildResponse("user", userAccountServices.getUserById(userId), "User fetched", HttpStatus.OK);
    }

    @Override
    @GetMapping("user/email/{email}")
    public ResponseEntity<Response> getUserByEmail(@PathVariable("email") String email) {
        return buildResponse.buildResponse("user", userAccountServices.getUserByEmail(email), "User fetched", HttpStatus.OK);
    }

    @Override
    @GetMapping("user/phone/{phoneNumber}")
    public ResponseEntity<Response> getUserByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        return buildResponse.buildResponse("user", userAccountServices.getUserByPhoneNumber(phoneNumber), "User fetched", HttpStatus.OK);
    }

    @Override
    @GetMapping("user/all")
    public ResponseEntity<Response> getAllUsers() {
        return buildResponse.buildResponse("user", userAccountServices.getAllUsers(), "Users fetched", HttpStatus.OK);
    }

    @Override
    @PutMapping("user/archive/{userId}")
    public ResponseEntity<Response> archiveUser(@PathVariable("userId") Integer userId) {
        return buildResponse.buildResponse("user", userAccountServices.archiveUser(userId), "User archived", HttpStatus.OK);
    }

    @PutMapping("password")
    @Override
    public ResponseEntity<Response> changePassword(UserPasswordDto userPasswordDto) throws Exception {
        return buildResponse.buildResponse("user", userAccountServices.changePassword(userPasswordDto), "Password updated", HttpStatus.OK);
    }
}
