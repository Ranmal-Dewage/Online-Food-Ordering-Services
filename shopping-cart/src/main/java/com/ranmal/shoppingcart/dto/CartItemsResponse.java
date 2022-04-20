package com.ranmal.shoppingcart.dto;

import com.ranmal.shoppingcart.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemsResponse {

    private Product product;
    private int boughtQuantity;
    private double totalPrice;
}
