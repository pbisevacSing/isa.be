package com.example.demo.controllers;

import com.example.demo.models.UserModel;
import com.example.demo.models.UserPageModel;
import com.example.demo.models.UserProductsModel;
import com.example.demo.services.IUserService;
import com.example.demo.services.SpecificUserService;
import jakarta.validation.Valid;
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
@CrossOrigin("*")
public class UserController {
    private final List<IUserService> userServices;

    @GetMapping("get-Implementer")
    public String getNameOfImplementer() {
        var test = "";
        for (IUserService userService : userServices)
        {
            var serviceNameTest1 = userService.getClass();
            var serviceNameTest2 = SpecificUserService.class;

            if (userService.getClass() == SpecificUserService.class)
            {
                test = userService.getNameOfImplementer();
                return test;
            }
        }

        return test;
    }

//    @GetMapping("get-list")
//    public List<UserModel> getList() {
//        return userService.findAll();
//    }
//
//    @GetMapping("get-user-products-list")
//    public List<UserProductsModel> getUserProductsList() {
//        return userService.findUserProductsAll();
//    }
//
//    @GetMapping("get-page-list")
//    public UserPageModel getPageList(Integer pageNumber, Integer pageSize) {
//        return userService.findPagedList(PageRequest.of(pageNumber, pageSize));
//    }
//
//    @PostMapping("create")
//    public ResponseEntity<?> create(@RequestBody @Valid UserModel userModel, BindingResult result) {
//        if (result.hasErrors()) {
//            return new ResponseEntity<>("Neuspesno registrovan!", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        return new ResponseEntity<>(userService.create(userModel), HttpStatus.CREATED);
//    }
}
