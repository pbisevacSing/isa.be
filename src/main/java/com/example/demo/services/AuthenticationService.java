package com.example.demo.services;

import com.example.demo.mappers.UserMapper;
import com.example.demo.models.LoginResponseModel;
import com.example.demo.models.LoginUserModel;
import com.example.demo.models.RegisterUserModel;
import com.example.demo.models.UserModel;
import com.example.demo.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    public UserModel signup(RegisterUserModel model) {
        var user = UserMapper.toRegisterEntity(model, passwordEncoder);
        var existingUser = userRepository.findByEmail(model.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var response = userRepository.save(user);
        return UserMapper.toModel(response);
    }

    public LoginResponseModel authenticate(LoginUserModel input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        var authenticatedUser = userRepository.findByEmail(input.getEmail())
                .orElseThrow();

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponseModel loginResponse = new LoginResponseModel();
        loginResponse.setToken(jwtToken);

        return loginResponse;
    }
}
