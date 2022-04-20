package com.ranmal.shoppingcart.controller;

import com.ranmal.shoppingcart.dto.CartItemsResponse;
import com.ranmal.shoppingcart.dto.ShoppingCartItemCreateDTO;
import com.ranmal.shoppingcart.exception.ApiExceptionResponse;
import com.ranmal.shoppingcart.exception.NotFoundException;
import com.ranmal.shoppingcart.model.ShoppingCartItem;
import com.ranmal.shoppingcart.model.ShoppingCartItemKeys;
import com.ranmal.shoppingcart.service.ShoppingCartItemService;
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

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/api/v1/shopping-cart-items", produces = APPLICATION_JSON_VALUE)
public class ShoppingCartItemController {

    private final ShoppingCartItemService shoppingCartItemService;

    @Autowired
    public ShoppingCartItemController(ShoppingCartItemService shoppingCartItemService) {
        this.shoppingCartItemService = shoppingCartItemService;
    }

    @Operation(summary = "Create New Shopping Cart Item", description = "Create a a new Shopping Cart Item and return the created basic shopping cart item details with ID", tags = "Shopping Cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Shopping Cart Item Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request for Shopping Cart Item Creation",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiExceptionResponse.class))
                    }),
    })
    @PostMapping
    public ResponseEntity<ShoppingCartItem> createShoppingCartItem(@Valid @RequestBody ShoppingCartItemCreateDTO shoppingCartItemCreateDTO) {
        ShoppingCartItem newShoppingCartItem = ShoppingCartItem.builder().
                cartId(shoppingCartItemCreateDTO.getCartId()).
                productId(shoppingCartItemCreateDTO.getProductId()).
                quantity(shoppingCartItemCreateDTO.getQuantity()).
                build();
        return new ResponseEntity<>(this.shoppingCartItemService.newShoppingCartItem(newShoppingCartItem), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Shopping Cart Item", description = "Delete the Shopping Cart Items and send the deleted keys as the response", tags = "Shopping Cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shopping Cart Item Deleted"),
            @ApiResponse(responseCode = "404", description = "Shopping Cart Item Not Found for Deletion",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiExceptionResponse.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad Request for Shopping Cart Item Deletion",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiExceptionResponse.class))
                    }),
    })
    @DeleteMapping(path = "/shopping-carts/{cartId}/products/{productId}")
    public ShoppingCartItemKeys deleteCartItem(@PathVariable("cartId") int cartId, @PathVariable("productId") int productId) {
        ShoppingCartItemKeys newShoppingCartItemKeys = new ShoppingCartItemKeys(productId, cartId);
        try {
            return this.shoppingCartItemService.removeCartItem(newShoppingCartItemKeys);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException("No resource found for Shopping Cart Item Entity with Cart Id : " + cartId + " and Product Id : " + productId);
        }
    }

    @Operation(summary = "Details of Shopping Cart Items",
            description = "Get Details of Shopping Cart Items inside a Shopping Cart by sending Cart Id", tags = "Shopping Cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shopping Cart Item Details Found"),
            @ApiResponse(responseCode = "404", description = "Shopping Cart Items Not Found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiExceptionResponse.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad Request for getting Shopping Cart Items Details",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiExceptionResponse.class))
                    }),
    })
    @GetMapping(path="/shopping-carts/{cartId}")
    public List<CartItemsResponse> getCartItemDetails(@PathVariable("cartId") int cartId){
        return this.shoppingCartItemService.shoppingCartItemDetails(cartId);
    }

}
