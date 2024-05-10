package id.ac.ui.cs.advprog.besell.service;

import id.ac.ui.cs.advprog.besell.model.Cart;
import id.ac.ui.cs.advprog.besell.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCartItems() {
        when(cartRepository.getCartItems()).thenReturn(Collections.singletonList(new Cart.Builder("123", "Product A").build()));

        List<Cart> cartItems = cartService.getCartItems();

        assertEquals(1, cartItems.size());
        assertEquals("123", cartItems.get(0).getProductId());
        assertEquals("Product A", cartItems.get(0).getProductName());

        verify(cartRepository, times(1)).getCartItems();
    }

    @Test
    void testAddItemToCart() throws ExecutionException, InterruptedException {
        Cart cartItem = new Cart.Builder("1", "Product A")
                .quantity(1)
                .price(10.0)
                .build();

        when(cartRepository.addItemToCart(cartItem)).thenReturn(cartItem);

        CompletableFuture<Cart> result = cartService.addItemToCart(cartItem);
        Cart addedItem = result.get(); // Wait for the asynchronous operation to complete

        assertNotNull(addedItem);
        assertEquals("1", addedItem.getProductId());
        assertEquals("Product A", addedItem.getProductName());
        assertEquals(1, addedItem.getQuantity());
        assertEquals(10.0, addedItem.getPrice());
    }
}
