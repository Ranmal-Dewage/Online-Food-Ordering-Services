package com.ranmal.shoppingcart.service;

import com.ranmal.shoppingcart.model.ShoppingCartItem;
import com.ranmal.shoppingcart.model.ShoppingCartItemKeys;
import com.ranmal.shoppingcart.repository.ShoppingCartItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingCartItemServiceTest {

    @Mock
    private ShoppingCartItemRepository shoppingCartItemRepositoryMock;
    private ShoppingCartItemService underTestShoppingCartItemService;

    @BeforeEach
    void setUp() {
        underTestShoppingCartItemService = new ShoppingCartItemService(shoppingCartItemRepositoryMock);
    }

    @Test
    void checkNewShoppingCartItem() {
        //given
        ShoppingCartItem testShoppingCartItem = ShoppingCartItem.builder().
                cartId(1).
                productId(3).
                quantity(100).
                build();
        given(shoppingCartItemRepositoryMock.save(testShoppingCartItem)).willReturn(testShoppingCartItem);

        //when
        ShoppingCartItem result = underTestShoppingCartItemService.newShoppingCartItem(testShoppingCartItem);

        //then
        assertThat(result).isInstanceOf(ShoppingCartItem.class);
    }

    @Test
    void removeCartItem() {
        //given
        ShoppingCartItemKeys testShoppingCartItemKeys = new ShoppingCartItemKeys(3,2);
        doNothing().when(shoppingCartItemRepositoryMock).deleteById(testShoppingCartItemKeys);

        //when
        ShoppingCartItemKeys result = underTestShoppingCartItemService.removeCartItem(testShoppingCartItemKeys);

        //then
        verify(shoppingCartItemRepositoryMock).deleteById(testShoppingCartItemKeys);
        assertThat(result).isInstanceOf(ShoppingCartItemKeys.class);
    }

}