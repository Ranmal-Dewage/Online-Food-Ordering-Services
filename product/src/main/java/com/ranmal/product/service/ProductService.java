package com.ranmal.product.service;

import com.ranmal.product.exception.NotFoundException;
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
        List<Product> availableProducts = this.productRepository.findAll();
        if (availableProducts.size() == 0) {
            throw new NotFoundException("No Products Available in the System");
        } else {
            return availableProducts;
        }
    }

    public Product createProducts(Product product) {
        return this.productRepository.save(product);
    }

    public List<Product> findProductsForSearch(String value) {
        if (!value.matches("^[a-zA-Z\\d| ]*$")) {
            throw new IllegalArgumentException("Search value can only contain Alphanumeric Characters Only");
        }

        List<Product> retrievedProducts = this.productRepository.findProductsByProductNameOrAndProductCategory(value);
        if (retrievedProducts.size() == 0) {
            throw new NotFoundException("No Products Found for Search Value : " + value);
        } else {
            return retrievedProducts;
        }
    }

    public List<Product> findProductsByCategory(String category) {
        if (!category.matches("^[a-zA-Z| ]*$")) {
            throw new IllegalArgumentException("Category value can only contain Alphabetic Characters Only");
        }

        List<Product> retrievedProducts = this.productRepository.findProductsByProductCategory(category);
        if (retrievedProducts.size() == 0) {
            throw new NotFoundException("No Products Found for the Category : " + category);
        } else {
            return retrievedProducts;
        }
    }
}
