package com.ranmal.product.service;

import com.ranmal.product.exception.NotFoundException;
import com.ranmal.product.model.Product;
import com.ranmal.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepositoryMock;
    private ProductService underTestProductService;

    @BeforeEach
    void setUp() {
        underTestProductService = new ProductService(productRepositoryMock);
    }

    @Test
    void checkFindAllProducts() {
        //given
        List<Product> expected = List.of(
                new Product(
                        1,
                        "Chicken",
                        "Meat",
                        8.25,
                        1000,
                        "test_url",
                        1),
                new Product(
                        2,
                        "Pineapple",
                        "Fruit",
                        3.25,
                        2000,
                        "test_url",
                        2)
        );
        given(productRepositoryMock.findAll()).willReturn(expected);

        //when
        List<Product> result = underTestProductService.findAllProducts();

        //then
        assertThat(result).isEqualTo(expected);

    }

    @Test
    void checkCreateProducts() {
        //given
        Product testData = Product.builder().
                productName("Chicken").
                productCategory("Meat").
                productPrice(8.25).
                imageUrl("test_url").
                quantity(1000).
                producerId(1).
                build();

        Product expected = new Product(
                1,
                "Chicken",
                "Meat",
                8.25,
                1000,
                "test_url",
                1);
        given(productRepositoryMock.save(testData)).willReturn(expected);

        //when
        Product result = underTestProductService.createProducts(testData);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void checkFindProductsForSearch() {
        //given
        String searchTestData = "c";

        List<Product> expected = List.of(
                new Product(
                        1,
                        "Chicken",
                        "Meat",
                        8.25,
                        1000,
                        "test_url",
                        1),
                new Product(
                        2,
                        "Carrot",
                        "Vegetable",
                        3.25,
                        2000,
                        "test_url",
                        2)
        );
        given(productRepositoryMock.findProductsByProductNameOrAndProductCategory(searchTestData)).willReturn(expected);

        //when
        List<Product> result = underTestProductService.findProductsForSearch(searchTestData);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void checkFindProductsByCategory() {
        //given
        String categoryTestData = "meat";

        List<Product> expected = List.of(
                new Product(
                        1,
                        "Chicken",
                        "Meat",
                        8.25,
                        1000,
                        "test_url",
                        1),
                new Product(
                        2,
                        "Lamb",
                        "Meat",
                        6.25,
                        2000,
                        "test_url",
                        1)
        );
        given(productRepositoryMock.findProductsByProductCategory(categoryTestData)).willReturn(expected);

        //when
        List<Product> result = underTestProductService.findProductsByCategory(categoryTestData);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void checkSearchProductIllegalArgument() {
        //given
        String searchTestData = "@$!";

        //when
        //then
        assertThatThrownBy(() -> underTestProductService.findProductsForSearch(searchTestData)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Search value can only contain Alphanumeric Characters Only");
    }

    @Test
    void checkSearchProductNotFound() {
        //given
        String searchTestData = "Tiara";
        List<Product> expected = List.of();
        given(productRepositoryMock.findProductsByProductNameOrAndProductCategory(searchTestData)).willReturn(expected);

        //when
        //then
        assertThatThrownBy(() -> underTestProductService.findProductsForSearch(searchTestData)).
                isInstanceOf(NotFoundException.class).
                hasMessageContaining("No Products Found for Search Value : " + searchTestData);
    }

    @Test
    void checkFindProductsByCategoryNotFound() {
        //given
        String categoryTestData = "meat";
        List<Product> expected = List.of();
        given(productRepositoryMock.findProductsByProductCategory(categoryTestData)).willReturn(expected);

        //when
        //then
        assertThatThrownBy(() -> underTestProductService.findProductsByCategory(categoryTestData)).
                isInstanceOf(NotFoundException.class).
                hasMessageContaining("No Products Found for the Category : " + categoryTestData);
    }

    @Test
    void checkFindProductsByCategoryIllegalArgumentException() {
        //given
        String categoryTestData = "meat@!";

        //when
        //then
        assertThatThrownBy(() -> underTestProductService.findProductsByCategory(categoryTestData)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Category value can only contain Alphabetic Characters Only");
    }

    @Test
    void checkFindAllProductsNotFound() {
        //given
        List<Product> expected = List.of();
        given(productRepositoryMock.findAll()).willReturn(expected);

        //when
        //then
        assertThatThrownBy(() -> underTestProductService.findAllProducts()).
                isInstanceOf(NotFoundException.class).
                hasMessageContaining("No Products Available in the System");
    }

}
