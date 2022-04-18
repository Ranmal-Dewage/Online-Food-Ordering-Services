package com.ranmal.shoppingcart.service;

import com.ranmal.shoppingcart.model.ShoppingCartItem;
import com.ranmal.shoppingcart.model.ShoppingCartItemKeys;
import com.ranmal.shoppingcart.repository.ShoppingCartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartItemService {

    private final ShoppingCartItemRepository shoppingCartItemRepository;

    @Autowired
    public ShoppingCartItemService(ShoppingCartItemRepository shoppingCartItemRepository) {
        this.shoppingCartItemRepository = shoppingCartItemRepository;
    }

    public ShoppingCartItem newShoppingCartItem(ShoppingCartItem shoppingCartItem) {
        // TODO : Check if passed Product Id and Cart Id exists
        // TODO : Check if Product Quantity added less than available
        // TODO : reduce from Quantity in Product Table
        return this.shoppingCartItemRepository.save(shoppingCartItem);
    }

    public ShoppingCartItemKeys removeCartItem(ShoppingCartItemKeys shoppingCartItemKeys) {
        this.shoppingCartItemRepository.deleteById(shoppingCartItemKeys);
        return shoppingCartItemKeys;
    }

}
