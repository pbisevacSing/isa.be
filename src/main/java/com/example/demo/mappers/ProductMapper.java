package com.example.demo.mappers;

import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.models.ProductModel;
import com.example.demo.models.UserModel;
import com.example.demo.models.UserPageModel;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {
    public static ProductModel toModel(Product entity) {
        return ProductModel.builder()
                .name(entity.getName()).build();
    }

    public static List<ProductModel> toModelList(List<Product> entities) {
        var list = new ArrayList<ProductModel>();

        for (var entity : entities) {
            list.add(toModel(entity));
        }

        return list;
    }
}
