package com.ranmal.product.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @NotNull
    private String productName;
    @NotNull
    private String productCategory;
    @NotNull
    private double productPrice;
    @NotNull
    private int quantity;
    @NotNull
    private String imageUrl;
    @NotNull
    private int producerId;

}
