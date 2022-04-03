package com.ranmal.product.repository;

import com.ranmal.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE p.productName LIKE %?1% OR p.productCategory LIKE %?1%")
    List<Product> findProductsByProductNameOrAndProductCategory(String value);

    @Query("SELECT p FROM Product p WHERE p.productCategory = ?1")
    List<Product> findProductsByProductCategory(String category);

}
