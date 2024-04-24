package id.ac.ui.cs.advprog.besell.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CartTest {

    @Test
    public void testCartBuilder() {
        Cart cart = new Cart.Builder("123", "Product A")
                .quantity(2)
                .price(10.99)
                .build();

        assertEquals("123", cart.getProductId());
        assertEquals("Product A", cart.getProductName());
        assertEquals(2, cart.getQuantity());
        assertEquals(10.99, cart.getPrice(), 0.01);
    }
}
