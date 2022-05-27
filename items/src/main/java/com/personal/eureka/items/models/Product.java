package com.personal.eureka.items.models;

import lombok.Data;

import java.util.Date;

@Data
public class Product {

    private Long productId;
    private String description;
    private Double price;
    private Date createdAt;
}
