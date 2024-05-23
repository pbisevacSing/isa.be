package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.models.LoginResponseModel;
import com.example.demo.models.LoginUserModel;
import com.example.demo.models.RegisterUserModel;
import com.example.demo.models.UserModel;
import com.example.demo.services.AuthenticationService;
import com.example.demo.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<UserModel> register(@RequestBody RegisterUserModel registerUserDto) {
        UserModel registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseModel> authenticate(@RequestBody LoginUserModel loginUserDto) {
        LoginResponseModel authenticatedUser = authenticationService.authenticate(loginUserDto);
        return ResponseEntity.ok(authenticatedUser);
    }
}
