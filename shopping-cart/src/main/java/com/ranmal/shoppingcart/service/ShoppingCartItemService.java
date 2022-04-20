package com.ranmal.shoppingcart.service;

import com.ranmal.shoppingcart.dto.CartItemsResponse;
import com.ranmal.shoppingcart.exception.NotFoundException;
import com.ranmal.shoppingcart.model.Product;
import com.ranmal.shoppingcart.model.ShoppingCartItem;
import com.ranmal.shoppingcart.model.ShoppingCartItemKeys;
import com.ranmal.shoppingcart.repository.ShoppingCartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartItemService {

    private final ShoppingCartItemRepository shoppingCartItemRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public ShoppingCartItemService(
            ShoppingCartItemRepository shoppingCartItemRepository,
            RestTemplate restTemplate
    ) {
        this.shoppingCartItemRepository = shoppingCartItemRepository;
        this.restTemplate = restTemplate;
    }

    public ShoppingCartItem newShoppingCartItem(ShoppingCartItem shoppingCartItem) {
        // TODO : Check if passed Product ID and Cart ID exists
        // TODO : Check if Product Quantity added less than available
        // TODO : reduce from Quantity in Product Table
        return this.shoppingCartItemRepository.save(shoppingCartItem);
    }

    public ShoppingCartItemKeys removeCartItem(ShoppingCartItemKeys shoppingCartItemKeys) {
        this.shoppingCartItemRepository.deleteById(shoppingCartItemKeys);
        return shoppingCartItemKeys;
    }

    public List<CartItemsResponse> shoppingCartItemDetails(int cartId) {
        List<ShoppingCartItem> cartItems = this.shoppingCartItemRepository.findShoppingCartItemsByCartId(cartId);
        if (cartItems.isEmpty()) {
            throw new NotFoundException("No Cart Items found for Shopping Cart with Cart Id : " + cartId);
        }
        return cartItems.stream().map((item) -> {
            Product product = restTemplate.getForObject("http://localhost:8081/api/v1/products/{productId}", Product.class, item.getProductId());
            double totalProductPrice = item.getQuantity() * (product != null ? product.getProductPrice() : 0);
            return new CartItemsResponse(product, item.getQuantity(), totalProductPrice);
        }).collect(Collectors.toList());
    }

}
