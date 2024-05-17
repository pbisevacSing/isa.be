package com.example.demo.repositories;

import com.example.demo.entities.Product;
import com.example.demo.entities.UserWithProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Integer> {
}
