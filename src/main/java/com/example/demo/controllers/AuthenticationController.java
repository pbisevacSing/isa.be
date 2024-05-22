package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.models.LoginResponseModel;
import com.example.demo.models.LoginUserModel;
import com.example.demo.models.RegisterUserModel;
import com.example.demo.services.AuthenticationService;
import com.example.demo.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserModel registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseModel> authenticate(@RequestBody LoginUserModel loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponseModel loginResponse = new LoginResponseModel();
        loginResponse.setToken(jwtToken);

        return ResponseEntity.ok(loginResponse);
    }
}
