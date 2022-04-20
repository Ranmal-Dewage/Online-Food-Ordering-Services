package com.ranmal.shoppingcart.service;

import com.ranmal.shoppingcart.dto.ShoppingCartDeleteResponse;
import com.ranmal.shoppingcart.model.ShoppingCart;
import com.ranmal.shoppingcart.repository.ShoppingCartItemRepository;
import com.ranmal.shoppingcart.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartItemRepository shoppingCartItemRepository;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ShoppingCartItemRepository shoppingCartItemRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
    }

    public ShoppingCart newShoppingCart(ShoppingCart shoppingCart) {
        // TODO : Check if passed User ID exists
        return this.shoppingCartRepository.saveAndFlush(shoppingCart);
    }

    @Transactional
    public ShoppingCartDeleteResponse removeShoppingCart(int cartId) {
        this.shoppingCartRepository.deleteById(cartId);
        this.shoppingCartItemRepository.deleteShoppingCartItemsByCartId(cartId);
        return new ShoppingCartDeleteResponse(cartId);
    }
}
