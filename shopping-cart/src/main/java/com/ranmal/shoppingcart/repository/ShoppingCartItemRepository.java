package com.ranmal.shoppingcart.repository;

import com.ranmal.shoppingcart.model.ShoppingCartItem;
import com.ranmal.shoppingcart.model.ShoppingCartItemKeys;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartItemRepository  extends JpaRepository<ShoppingCartItem, ShoppingCartItemKeys> {

    void deleteShoppingCartItemsByCartId(int cartId);

}
