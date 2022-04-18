package com.ranmal.shoppingcart.service;


import com.ranmal.shoppingcart.dto.ShoppingCartDeleteResponse;
import com.ranmal.shoppingcart.model.ShoppingCart;
import com.ranmal.shoppingcart.repository.ShoppingCartItemRepository;
import com.ranmal.shoppingcart.repository.ShoppingCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepositoryMock;
    @Mock
    private ShoppingCartItemRepository shoppingCartItemRepositoryMock;
    private ShoppingCartService underTestShoppingCartService;

    @BeforeEach
    void setUp() {
        underTestShoppingCartService = new ShoppingCartService(shoppingCartRepositoryMock, shoppingCartItemRepositoryMock);
    }

    @Test
    void checkNewShoppingCart() {
        //given
        ShoppingCart testShoppingCart = ShoppingCart.builder().
                cartName("Cart 1").
                userId(2).
                build();
        given(underTestShoppingCartService.newShoppingCart(testShoppingCart)).willAnswer((invocation) -> {
            ShoppingCart shoppingCart = invocation.getArgument(0, ShoppingCart.class);
            shoppingCart.setCartId(2);
            return shoppingCart;
        });

        //when
        ShoppingCart result = underTestShoppingCartService.newShoppingCart(testShoppingCart);

        //then
        assertThat(result).isInstanceOf(ShoppingCart.class);
    }

    @Test
    void checkRemoveShoppingCart() {
        //given
        int testCartId = 2;
        doNothing().when(shoppingCartRepositoryMock).deleteById(testCartId);
        doNothing().when(shoppingCartItemRepositoryMock).deleteShoppingCartItemsByCartId(testCartId);

        //when
        ShoppingCartDeleteResponse result = underTestShoppingCartService.removeShoppingCart(testCartId);

        //then
        verify(shoppingCartRepositoryMock).deleteById(testCartId);
        verify(shoppingCartItemRepositoryMock).deleteShoppingCartItemsByCartId(testCartId);
        assertThat(result).isInstanceOf(ShoppingCartDeleteResponse.class);
    }

}