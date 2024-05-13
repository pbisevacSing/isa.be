package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.models.UserModel;
import com.example.demo.repositories.IUserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final IUserRepository userRepository;

    @CrossOrigin("*")
    @GetMapping("get-first-name")
    public String getFirstName() {
        return "Petar";
    }

    @GetMapping("get-first-name-list")
    public List<User> getFirstNameList() {
        return userRepository.findAll();
    }

    @PostMapping("create-user")
    public boolean createUser(String firstName, String lastName) {
        return true;
    }

    @PostMapping("create-user-body")
    public ResponseEntity<?> createUserBody(@RequestBody @Valid UserModel userModel, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Neuspesno registrovan!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<UserModel>(userModel, HttpStatus.CREATED);
    }
}
