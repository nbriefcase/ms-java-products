package com.personal.eureka.items.service;

import com.personal.eureka.items.client.ProductRestClient;
import com.personal.eureka.items.models.Item;
import com.personal.eureka.items.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
public class ItemServiceFeign implements IItemService {

    @Autowired
    private ProductRestClient productRestClient;

    @Override
    public List<Item> findAll() {
        List<Product> productList = productRestClient.findAll();
        return productList.stream().map(product ->
                new Item(product, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer amount) {
        Product product = productRestClient.findById(id);
        return new Item(product, amount);
    }
}
