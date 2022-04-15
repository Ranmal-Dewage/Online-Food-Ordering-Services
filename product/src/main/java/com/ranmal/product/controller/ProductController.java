package com.ranmal.product.controller;

import com.ranmal.product.dto.ProductDTO;
import com.ranmal.product.exception.ApiExceptionResponse;
import com.ranmal.product.service.ProductService;
import com.ranmal.product.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "api/v1/products", produces = APPLICATION_JSON_VALUE)
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Get All Products", description = "Return a list of Products", tags = "Products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Products",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Products Not Found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiExceptionResponse.class))
                    }),
    })
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAllProducts();
    }

    @Operation(summary = "Search Products", description = "Search Products by Name or Category and return a list of Products", tags = "Products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Products",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Products Not Found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiExceptionResponse.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad Request for Product Search",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiExceptionResponse.class))
                    }),
    })
    @GetMapping(path = "/search")
    public List<Product> searchProducts(@RequestParam(name = "value") String value) {
        return this.productService.findProductsForSearch(value);
    }

    @Operation(summary = "Get Products By Category", description = "Return a list of Products to the given Category", tags = "Products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Products",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Products Not Found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiExceptionResponse.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad Request for Product Category",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiExceptionResponse.class))
                    }),
    })
    @GetMapping(path = "/{category}")
    public List<Product> getProductsByCategory(@PathVariable(name = "category") String category) {
        return this.productService.findProductsByCategory(category);
    }

    @Operation(summary = "Create Products", description = "Create a Product and return the created product with ID", tags = "Products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request for Product Creation",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiExceptionResponse.class))
                    }),
    })
    @PostMapping
    public ResponseEntity<Product> addNewProduct(@Valid @RequestBody() ProductDTO productDTO) {
        Product newProduct = Product.builder().
                productName(productDTO.getProductName()).
                productCategory(productDTO.getProductCategory()).
                productPrice(productDTO.getProductPrice()).
                imageUrl(productDTO.getImageUrl()).
                quantity(productDTO.getQuantity()).
                producerId(productDTO.getProducerId()
                ).build();
        return new ResponseEntity<>(this.productService.createProducts(newProduct), HttpStatus.CREATED);
    }
}
