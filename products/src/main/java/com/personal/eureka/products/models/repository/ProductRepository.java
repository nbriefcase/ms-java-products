package com.personal.eureka.products.models.repository;

import com.personal.eureka.products.models.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
