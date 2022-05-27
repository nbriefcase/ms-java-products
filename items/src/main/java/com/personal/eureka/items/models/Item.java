package com.personal.eureka.items.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private Product product;
    private Integer amount;

    public Double getTotal() {
        return product.getPrice() * this.getAmount();
    }
}
