package com.personal.eureka.products.service;

import com.personal.eureka.products.models.entity.Product;

import java.util.List;

public interface IProductService {

    List<Product> findAll();

    Product findById(Long id);
}
