package com.personal.eureka.items.service;

import com.personal.eureka.items.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ItemServiceImpl implements IItemService {

    @Autowired
    private RestTemplate restClient;

    @Override
    public List<Item> findAll() {
        return null;
    }

    @Override
    public Item findById(Long id, Integer amount) {
        return null;
    }
}
