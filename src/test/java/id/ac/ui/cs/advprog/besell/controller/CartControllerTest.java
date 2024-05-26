package id.ac.ui.cs.advprog.besell.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import id.ac.ui.cs.advprog.besell.model.Cart;
import id.ac.ui.cs.advprog.besell.service.CartService;
import id.ac.ui.cs.advprog.besell.service.JwtService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CartControllerTest {

    @Mock
    private CartService cartService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetCartItems() throws JsonProcessingException {
        String token = "validToken";
        List<Cart> cartItems = new ArrayList<>();
        cartItems.add(new Cart.Builder("123", "Product 1").quantity(1).price(10.0).build());
        cartItems.add(new Cart.Builder("456", "Product 2").quantity(2).price(20.0).build());
        CompletableFuture<String> expectedResponse = CompletableFuture.completedFuture("[{\"productId\":\"123\",\"productName\":\"Product 1\",\"quantity\":1,\"price\":10.0},{\"productId\":\"456\",\"productName\":\"Product 2\",\"quantity\":2,\"price\":20.0}]");

        when(jwtService.resolveClaims(token)).thenReturn(mock(Claims.class));
        when(jwtService.validateClaims(any())).thenReturn(true);
        when(cartService.getCartItems()).thenReturn(cartItems);

        CompletableFuture<String> response = cartController.getCartItems(token);
        assertEquals(expectedResponse.join(), response.join());
    }

    @Test
    void testUpdateItemQuantity() {
        String token = "validToken";
        String productId = "123";
        int quantity = 5;
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Item quantity updated");

        when(jwtService.resolveClaims(token)).thenReturn(mock(Claims.class));
        when(jwtService.validateClaims(any())).thenReturn(true);

        ResponseEntity<String> response = cartController.updateItemQuantity(token, productId, quantity);
        assertEquals(expectedResponse, response);
    }

    @Test
    void testRemoveItemFromCart() {
        String token = "validToken";
        String productId = "123";
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Item removed from cart");

        when(jwtService.resolveClaims(token)).thenReturn(mock(Claims.class));
        when(jwtService.validateClaims(any())).thenReturn(true);

        ResponseEntity<String> response = cartController.removeItemFromCart(token, productId);
        assertEquals(expectedResponse, response);
    }
}
