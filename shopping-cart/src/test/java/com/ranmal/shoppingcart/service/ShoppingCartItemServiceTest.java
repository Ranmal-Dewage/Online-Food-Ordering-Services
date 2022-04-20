package com.ranmal.shoppingcart.service;

import com.ranmal.shoppingcart.dto.CartItemsResponse;
import com.ranmal.shoppingcart.exception.NotFoundException;
import com.ranmal.shoppingcart.model.Product;
import com.ranmal.shoppingcart.model.ShoppingCartItem;
import com.ranmal.shoppingcart.model.ShoppingCartItemKeys;
import com.ranmal.shoppingcart.repository.ShoppingCartItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingCartItemServiceTest {

    @Mock
    private ShoppingCartItemRepository shoppingCartItemRepositoryMock;
    @Mock
    private RestTemplate restTemplateMock;
    private ShoppingCartItemService underTestShoppingCartItemService;

    @BeforeEach
    void setUp() {
        underTestShoppingCartItemService = new ShoppingCartItemService(shoppingCartItemRepositoryMock, restTemplateMock);
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
        ShoppingCartItemKeys testShoppingCartItemKeys = new ShoppingCartItemKeys(3, 2);
        doNothing().when(shoppingCartItemRepositoryMock).deleteById(testShoppingCartItemKeys);

        //when
        ShoppingCartItemKeys result = underTestShoppingCartItemService.removeCartItem(testShoppingCartItemKeys);

        //then
        verify(shoppingCartItemRepositoryMock).deleteById(testShoppingCartItemKeys);
        assertThat(result).isInstanceOf(ShoppingCartItemKeys.class);
    }

    @Test
    void checkShoppingCartItemDetails() {
        //given
        String testProductMicroserviceURL = "http://localhost:8081/api/v1/products/{productId}";
        int testCartId = 2;
        List<ShoppingCartItem> testShoppingCartItems = List.of(
                new ShoppingCartItem(3, 2, 500)
        );
        Product testProduct = new Product(3, "Chicken", "Meat", 8.25, 1);

        given(shoppingCartItemRepositoryMock.findShoppingCartItemsByCartId(testCartId)).willReturn(testShoppingCartItems);
        given(restTemplateMock.getForObject(testProductMicroserviceURL, Product.class, testProduct.getProductId())).willReturn(testProduct);

        //when
        List<CartItemsResponse> result = underTestShoppingCartItemService.shoppingCartItemDetails(testCartId);

        //then
        verify(shoppingCartItemRepositoryMock).findShoppingCartItemsByCartId(testCartId);
        verify(restTemplateMock).getForObject(testProductMicroserviceURL, Product.class, testProduct.getProductId());
        assertThat(result.get((int) (Math.random() * result.size()))).isInstanceOf(CartItemsResponse.class);
    }

    @Test
    void checkShoppingCartItemNotFound() {
        //given
        String testProductMicroserviceURL = "http://localhost:8081/api/v1/products/{productId}";
        int testCartId = 200;
        List<ShoppingCartItem> testShoppingCartItems = List.of();
        given(shoppingCartItemRepositoryMock.findShoppingCartItemsByCartId(testCartId)).willReturn(testShoppingCartItems);

        //when
        //then
        assertThatThrownBy(() -> underTestShoppingCartItemService.shoppingCartItemDetails(testCartId)).
                isInstanceOf(NotFoundException.class).
                hasMessageContaining("No Cart Items found for Shopping Cart with Cart Id : " + testCartId);

    }

}