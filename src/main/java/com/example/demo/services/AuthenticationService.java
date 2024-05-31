package com.example.demo.services;

import com.example.demo.entities.Token;
import com.example.demo.entities.User;
import com.example.demo.enums.TokenTypeEnum;
import com.example.demo.exceptions.user.UserAlreadyExistException;
import com.example.demo.mappers.UserMapper;
import com.example.demo.models.LoginResponseModel;
import com.example.demo.models.LoginUserModel;
import com.example.demo.models.RegisterUserModel;
import com.example.demo.models.UserModel;
import com.example.demo.repositories.ITokenRepository;
import com.example.demo.repositories.IUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final IUserRepository userRepository;
    private final ITokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserModel signup(RegisterUserModel model) {
        var user = UserMapper.toEntity(model, passwordEncoder);

        var existingUser = userRepository.findByEmail(model.getEmail());

        if (existingUser.isPresent())
            throw new UserAlreadyExistException("User with email " + model.getEmail() + " already exists");

        var savedUser = userRepository.save(user);

        return UserMapper.toModel(savedUser);
    }

    public LoginResponseModel authenticate(LoginUserModel model) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        model.getEmail(),
                        model.getPassword()
                )
        );

        var authenticatedUser = userRepository.findByEmail(model.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + model.getEmail() + " not found"));

        String jwtToken = jwtService.generateToken(authenticatedUser);
        String refreshToken = jwtService.generateRefreshToken(authenticatedUser);

        revokeAllUserTokens(authenticatedUser.getId());
        saveUserToken(authenticatedUser, jwtToken, refreshToken);

        return LoginResponseModel.builder()
                .token(jwtToken)
                .refreshToken(refreshToken).build();
    }

    private void saveUserToken(User user, String jwtToken, String refreshToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .refreshToken(refreshToken)
                .tokenType(TokenTypeEnum.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Integer userId) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(userId);

        if (validUserTokens.isEmpty())
            return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    public LoginResponseModel refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    )  {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new AuthorizationServiceException("Unauthorized");

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        var user = userRepository.findByEmail(userEmail);

        if (user.isEmpty())
            throw  new UsernameNotFoundException("User with email " + userEmail + " not found");

        var userDetails = user.get();

        if (!jwtService.isTokenValid(refreshToken, userDetails))
            throw new AuthorizationServiceException("Unauthorized");

        var accessToken = jwtService.generateToken(userDetails);
        String refToken = jwtService.generateRefreshToken(userDetails);

        revokeAllUserTokens(userDetails.getId());
        saveUserToken(userDetails, accessToken, refToken);

        return LoginResponseModel.builder()
                .token(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
