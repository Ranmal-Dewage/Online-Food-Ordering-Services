package com.ranmal.shoppingcart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartCreateDTO {

    @NotNull(message = "Cart Name can not be Null")
    @Pattern(regexp = "^[a-zA-Z\\d| ]*$", message = "Cart Name should contain Alpha-Numeric Characters only")
    private String cartName;

    @NotNull(message = "User Id can not be Null")
    @Min(value = 1, message = "User Id can not be Null")
    private int userId;

}
