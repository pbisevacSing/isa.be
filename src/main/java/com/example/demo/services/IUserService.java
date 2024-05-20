package com.example.demo.services;

import com.example.demo.models.UserModel;
import com.example.demo.models.UserPageModel;
import com.example.demo.models.UserProductsModel;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IUserService {
    List<UserModel> findAll();
    UserPageModel findPagedList(PageRequest pageRequest);
    UserModel create(UserModel model);
    List<UserProductsModel> findUserProductsAll();
    String getNameOfImplementer();
}
