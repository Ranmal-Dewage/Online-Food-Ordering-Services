package com.ranmal.shoppingcart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartItemCreateDTO {

    @NotNull(message = "Product Id can not be Null")
    @Min(value = 1, message = "Product Id can not be Null")
    private int productId;

    @NotNull(message = "Cart Id can not be Null")
    @Min(value = 1, message = "Cart Id can not be Null")
    private int cartId;

    @NotNull(message = "Quantity can not be Null")
    @Min(value = 1, message = "Quantity can not be Null")
    private int quantity;

}
