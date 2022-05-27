package com.personal.eureka.items.client;

import com.personal.eureka.items.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "product-service", url = "localhost:8001")
public interface ProductRestClient {

    @GetMapping("/products")
    List<Product> findAll();

    @GetMapping("/product/{id}")
    Product findById(@PathVariable Long id);
}
