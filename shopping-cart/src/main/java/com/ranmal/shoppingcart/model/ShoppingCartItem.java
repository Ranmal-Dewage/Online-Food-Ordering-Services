package com.ranmal.shoppingcart.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(ShoppingCartItemKeys.class)
public class ShoppingCartItem {

    @Id
    private int productId;
    @Id
    private int cartId;
    private int quantity;

}
