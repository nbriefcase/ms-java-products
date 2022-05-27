package com.personal.eureka.items.service;

import com.personal.eureka.items.models.Item;

import java.util.List;

public interface IItemService {

    List<Item> findAll();

    Item findById(Long id, Integer amount);
}
