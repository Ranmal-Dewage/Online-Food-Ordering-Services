package com.ranmal.product.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Getter
@Setter
@ToString
public class Product {

    @Id
    @GeneratedValue
    private int productId;
    private String productName;
    private String productCategory;
    private double productPrice;
    private int quantity;
    private String imageUrl;
    private int producerId;

}
