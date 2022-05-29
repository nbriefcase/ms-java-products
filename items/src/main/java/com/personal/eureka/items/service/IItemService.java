package com.personal.eureka.items.service;

import com.personal.eureka.items.models.Item;
import com.personal.eureka.items.models.Product;

import java.util.List;

public interface IItemService {

    List<Item> findAll();

    Item findById(Long id, Integer amount);

    Product save(Product product);

    Product update(Product product, Long id);

    void delete(Long id);
}
