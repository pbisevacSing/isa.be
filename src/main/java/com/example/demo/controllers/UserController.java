package com.example.demo.controllers;

import com.example.demo.models.UserModel;
import com.example.demo.models.UserPageModel;
import com.example.demo.models.UserProductsModel;
import com.example.demo.services.IUserService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping("get-list")
    public List<UserModel> getList() {
        return userService.findAll();
    }

    @GetMapping("get-user-products-list")
    public List<UserProductsModel> getUserProductsList() {
        return userService.findUserProductsAll();
    }

    @GetMapping("get-page-list")
    public UserPageModel getPageList(Integer pageNumber, Integer pageSize) {
        return userService.findPagedList(PageRequest.of(pageNumber, pageSize));
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody @Valid UserModel userModel, BindingResult result) {
        return ResponseEntity.ok(userService.create(userModel));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody @Valid UserModel userModel, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Not updated!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(userService.update(userModel));
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(Integer userId) {
        userService.delete(userId);
        return ResponseEntity.ok("");
    }
}
