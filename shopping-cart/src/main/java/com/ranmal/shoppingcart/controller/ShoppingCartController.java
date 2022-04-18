package com.ranmal.shoppingcart.controller;

import com.ranmal.shoppingcart.dto.ShoppingCartCreateDTO;
import com.ranmal.shoppingcart.dto.ShoppingCartDeleteResponse;
import com.ranmal.shoppingcart.exception.ApiExceptionResponse;
import com.ranmal.shoppingcart.exception.NotFoundException;
import com.ranmal.shoppingcart.model.ShoppingCart;
import com.ranmal.shoppingcart.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/api/v1/shopping-carts", produces = APPLICATION_JSON_VALUE)
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @Operation(summary = "Create New Shopping Cart", description = "Create a a new Shopping Cart and return the created basic shopping cart details with ID", tags = "Shopping Cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Shopping Cart Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request for Shopping Cart Creation",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiExceptionResponse.class))
                    }),
    })
    @PostMapping
    public ResponseEntity<ShoppingCart> createShoppingCart(@Valid @RequestBody ShoppingCartCreateDTO shoppingCartCreateDTO) {
        ShoppingCart newShoppingCart = ShoppingCart.builder().
                cartName(shoppingCartCreateDTO.getCartName()).
                userId(shoppingCartCreateDTO.getUserId()).
                build();
        return new ResponseEntity<>(this.shoppingCartService.newShoppingCart(newShoppingCart), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Shopping Cart", description = "Delete the Shopping Cart including its Shopping Cart Items", tags = "Shopping Cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shopping Cart Deleted"),
            @ApiResponse(responseCode = "404", description = "Shopping Cart Not Found for Deletion",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiExceptionResponse.class))
                    }),
    })
    @DeleteMapping(path = "{cartId}")
    public ShoppingCartDeleteResponse deleteShoppingCart(@PathVariable("cartId") int cartId) {
        try {
            return this.shoppingCartService.removeShoppingCart(cartId);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException("No resource found for Shopping Cart Entity with Cart Id : " + cartId);
        }
    }

}
