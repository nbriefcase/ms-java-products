package com.personal.eureka.products.controller;

import com.personal.eureka.products.models.entity.Product;
import com.personal.eureka.products.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping(value = "/products")
    public List<Product> list() {
        return productService.findAll();
    }

    @GetMapping(value = "/product/{id}")
    public Product get(@PathVariable Long id) throws InterruptedException {

        if (id.equals(10L)) {
            throw new IllegalStateException("Product not found!");
        } else if (id.equals(7L)) {
            TimeUnit.SECONDS.sleep(5);
        }

        return productService.findById(id);
    }
}
