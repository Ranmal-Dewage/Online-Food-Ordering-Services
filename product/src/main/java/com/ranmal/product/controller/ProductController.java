package com.ranmal.product.controller;

import com.ranmal.product.service.ProductService;
import com.ranmal.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping(path = "/search")
    public List<Product> searchProducts(@RequestParam(name = "value") String value){
        return this.productService.findProductsForSearch(value);
    }

    @GetMapping(path="/{category}")
    public List<Product> getProductsByCategory(@PathVariable(name ="category") String category){
        return this.productService.findProductsByCategory(category);
    }

    @PostMapping
    public Product addNewProduct(@RequestBody Product product){
        return this.productService.createProducts(product);
    }
}
