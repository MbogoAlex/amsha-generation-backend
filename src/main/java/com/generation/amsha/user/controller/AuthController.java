package com.generation.amsha.user.controller;

import com.generation.amsha.response.BuildResponse;
import com.generation.amsha.response.Response;
import com.generation.amsha.security.JWTGenerator;
import com.generation.amsha.user.dao.UserAccountDao;
import com.generation.amsha.user.dto.UserLoginDto;
import com.generation.amsha.user.dto.UserPasswordDto;
import com.generation.amsha.user.dto.UserRegistrationDto;
import com.generation.amsha.user.model.UserAccount;
import com.generation.amsha.user.services.UserAccountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    private BuildResponse buildResponse = new BuildResponse();
    private final AuthenticationManager authenticationManager;
    private final UserAccountServices userAccountService;
    private final UserAccountDao userAccountDao;
    private final JWTGenerator jwtGenerator;
    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            UserAccountServices userAccountService,
            UserAccountDao userAccountDao,
            JWTGenerator jwtGenerator
    ) {
        this.authenticationManager = authenticationManager;
        this.userAccountService = userAccountService;
        this.userAccountDao = userAccountDao;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("register")
    public ResponseEntity<Response> register(@RequestBody UserRegistrationDto userRegistrationDto) {
        if(userAccountDao.existsByPhoneNumber(userRegistrationDto.getPhoneNumber())) {
            return buildResponse.buildResponse(null, null, "User exists", HttpStatus.CONFLICT);
        }

        return buildResponse.buildResponse("user", userAccountService.register(userRegistrationDto), "User registered", HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<Response> login(@RequestBody UserLoginDto userLoginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginDto.getEmail(),
                            userLoginDto.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);

            return buildResponse.buildResponse("user", userAccountService.login(userLoginDto.getEmail(), token), "User logged in", HttpStatus.OK);

        } catch (AuthenticationException e) {
            return buildResponse.buildResponse(null, null, "Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
    }
}
