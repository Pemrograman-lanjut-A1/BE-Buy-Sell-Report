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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        // Mocking behavior of CartRepository
        when(cartRepository.getCartItems()).thenReturn(Collections.singletonList(new Cart.Builder("123", "Product A").build()));

        // Calling the method to be tested
        List<Cart> cartItems = cartService.getCartItems();

        // Verifying the result
        assertEquals(1, cartItems.size());
        assertEquals("123", cartItems.get(0).getProductId());
        assertEquals("Product A", cartItems.get(0).getProductName());

        // Verifying that CartRepository's method was called
        verify(cartRepository, times(1)).getCartItems();
    }

    // Write more tests for CartServiceImpl methods here
}
