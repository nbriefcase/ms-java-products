package com.personal.eureka.items.client;

import com.personal.eureka.items.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductRestClient {

    @GetMapping("/products")
    List<Product> findAll();

    @GetMapping("/product/{id}")
    Product findById(@PathVariable Long id);

    @PostMapping("/product")
    Product create(@RequestBody Product product);

    @PutMapping("/product/{id}")
    Product update(@RequestBody Product product, @PathVariable Long id);

    @DeleteMapping("/product/{id}")
    void delete(@PathVariable Long id);
}
