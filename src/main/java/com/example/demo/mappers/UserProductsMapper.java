package com.example.demo.mappers;

import com.example.demo.entities.UserProducts;
import com.example.demo.models.UserProductsModel;

import java.util.ArrayList;
import java.util.List;

public class UserProductsMapper {
    public static UserProductsModel toModel(UserProducts entity) {
        return UserProductsModel.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName()).products(ProductMapper.toModelList(entity.getProducts())).build();
    }

    public static List<UserProductsModel> toModelList(List<UserProducts> entities) {
        var list = new ArrayList<UserProductsModel>();
        for (var entity : entities) {
            list.add(toModel(entity));
        }
        return list;
    }
}
