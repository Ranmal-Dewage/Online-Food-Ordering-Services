package com.ranmal.product.service;

import com.ranmal.product.model.Product;
import com.ranmal.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAllProducts() {
        return this.productRepository.findAll();
    }

    public Product createProducts(Product product) {
        return this.productRepository.save(product);
    }

    public List<Product> findProductsForSearch(String value) {
        return this.productRepository.findProductsByProductNameOrAndProductCategory(value);
    }

    public List<Product> findProductsByCategory(String category){
        return this.productRepository.findProductsByProductCategory(category);
    }
}
