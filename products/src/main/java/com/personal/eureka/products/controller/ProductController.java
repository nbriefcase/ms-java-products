package com.personal.eureka.products.controller;

import com.personal.eureka.products.models.entity.Product;
import com.personal.eureka.products.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping(value = "/products")
    public List<Product> list() {
        return productService.findAll();
    }

    @GetMapping(value = "/product/{id}")
    public Product get(@PathVariable Long id) {
        return productService.findById(id);
    }
}
