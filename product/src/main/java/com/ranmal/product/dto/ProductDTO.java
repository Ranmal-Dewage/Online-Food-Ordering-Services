package com.ranmal.product.dto;

import lombok.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @NotNull(message = "Product Name can not be Null")
    @Pattern(regexp = "^[a-zA-Z| ]*$", message = "Product Name should contain Alphabetic Characters only")
    private String productName;

    @NotNull(message = "Product Category can not be Null")
    @Pattern(regexp = "^[a-zA-Z| ]*$", message = "Product Category should contain Alphabetic Characters only")
    private String productCategory;

    @NotNull(message = "Product Price can not be Null")
    @Digits(integer = 20, fraction = 2, message = "Product Price should contain Numeric Values with Two Decimal places only")
    private double productPrice;

    @NotNull(message = "Quantity can not be Null")
    @Min(value = 1, message = "Quantity can not be Null")
    private int quantity;

    @NotNull(message = "Image URL can not be Null")
    private String imageUrl;

    @NotNull(message = "Producer Id can not be Null")
    @Min(value = 1, message = "Producer Id can not be Null")
    private int producerId;

}
