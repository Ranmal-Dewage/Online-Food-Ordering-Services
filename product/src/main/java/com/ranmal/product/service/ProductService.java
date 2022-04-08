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
        return this.productRepository.findAll();
    }

    public Product createProducts(Product product) {
        return this.productRepository.save(product);
    }

    public List<Product> findProductsForSearch(String value) {
        if (!value.matches("^[a-zA-Z0-9]*$")) {
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
        List<Product> retrievedProducts = this.productRepository.findProductsByProductCategory(category);
        if (retrievedProducts.size() == 0) {
            throw new NotFoundException("No Products Found for the Category : " + category);
        } else {
            return retrievedProducts;
        }
    }
}
