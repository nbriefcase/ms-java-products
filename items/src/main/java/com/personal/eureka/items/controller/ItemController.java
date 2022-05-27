package com.personal.eureka.items.controller;

import com.personal.eureka.items.models.Item;
import com.personal.eureka.items.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    private IItemService itemService;

    @GetMapping(value = "/items")
    public List<Item> list() {
        return itemService.findAll();
    }

    @GetMapping(value = "/item/{id}/amount/{amount}")
    public Item get(@PathVariable Long id, @PathVariable Integer amount) {
        return itemService.findById(id, amount);
    }
}
