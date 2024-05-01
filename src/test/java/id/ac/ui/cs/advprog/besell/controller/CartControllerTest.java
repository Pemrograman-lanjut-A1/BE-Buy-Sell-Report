package id.ac.ui.cs.advprog.besell.controller;

import id.ac.ui.cs.advprog.besell.model.Cart;
import id.ac.ui.cs.advprog.besell.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartService cartService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddItemToCart() {
        Cart cartItem = new Cart.Builder("123", "Test Product")
                .quantity(1)
                .price(10.0)
                .build();
        ResponseEntity<String> responseEntity = cartController.addItemToCart(cartItem);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Item added to cart", responseEntity.getBody());
    }

    @Test
    public void testGetCartItems() {
        List<Cart> expectedCartItems = Arrays.asList(
                new Cart.Builder("123", "Test Product 1").quantity(1).price(10.0).build(),
                new Cart.Builder("456", "Test Product 2").quantity(2).price(20.0).build()
        );
        when(cartService.getCartItems()).thenReturn(expectedCartItems);

        ResponseEntity<List<Cart>> responseEntity = cartController.getCartItems();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedCartItems, responseEntity.getBody());
    }

    @Test
    public void testUpdateItemQuantity() {
        String productId = "123";
        int quantity = 5;

        ResponseEntity<String> responseEntity = cartController.updateItemQuantity(productId, quantity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Item quantity updated", responseEntity.getBody());
    }

    @Test
    public void testRemoveItemFromCart() {
        String productId = "123";

        ResponseEntity<String> responseEntity = cartController.removeItemFromCart(productId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Item removed from cart", responseEntity.getBody());
    }
}
