package com.example.demo.repositories;

import com.example.demo.entities.UserProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserProductsRepository extends JpaRepository<UserProducts, Integer> {
}
