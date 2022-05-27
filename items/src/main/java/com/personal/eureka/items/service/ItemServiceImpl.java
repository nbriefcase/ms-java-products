package com.personal.eureka.items.service;

import com.personal.eureka.items.models.Item;
import com.personal.eureka.items.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements IItemService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Item> findAll() {
        List<Item> results = new ArrayList<>();
        Product[] products = restTemplate.getForObject("http://localhost:8001/products", Product[].class);
        if (products != null) {
            List<Product> productList = Arrays.asList(products);
            results = productList.stream().map(product ->
                    new Item(product, 1)).collect(Collectors.toList());
        }
        return results;
    }

    @Override
    public Item findById(Long id, Integer amount) {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", id.toString());
        Product product = restTemplate.getForObject("http://localhost:8001/product/{1}", Product.class, pathVariables);
        return new Item(product, amount);
    }
}
