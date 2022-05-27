package com.personal.eureka.items.controller;

import com.personal.eureka.items.models.Item;
import com.personal.eureka.items.models.Product;
import com.personal.eureka.items.service.IItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class ItemController {

    @Autowired
    private IItemService itemService;

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    @GetMapping(value = "/items")
    public List<Item> list(@RequestHeader(name = "token-request") String token, @RequestParam(name = "name") String name) {
        System.out.println("token:" + token);
        System.out.println("name:" + name);
        return itemService.findAll();
    }

    @GetMapping(value = "/item/{id}/amount/{amount}")
    public Item get(@PathVariable Long id, @PathVariable Integer amount) {
        return circuitBreakerFactory.create("items")
                .run(() -> itemService.findById(id, amount), e -> defaultItem(id, amount, e));
    }

    private Item defaultItem(Long id, Integer amount, Throwable ex) {
        log.error(ex.getMessage());
        Product product = new Product();
        product.setProductId(id);
        product.setDescription("Default Product");
        product.setPrice(0.0);
        return new Item(product, amount);
    }
}
