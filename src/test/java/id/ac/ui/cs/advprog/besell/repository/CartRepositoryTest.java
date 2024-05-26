package id.ac.ui.cs.advprog.besell.repository;

import id.ac.ui.cs.advprog.besell.model.Cart;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CartRepositoryTest {

    @Test
    public void testAddItemToCart() {
        CartRepository cartRepository = new CartRepository();
        Cart cartItem = new Cart.Builder("123", "Product A")
                .quantity(2)
                .price(10.99)
                .build();

        Cart addedItem = cartRepository.addItemToCart(cartItem);

        assertNotNull(addedItem);
        assertEquals(cartItem.getProductId(), addedItem.getProductId());
        assertEquals(cartItem.getProductName(), addedItem.getProductName());
        assertEquals(cartItem.getQuantity(), addedItem.getQuantity());
        assertEquals(cartItem.getPrice(), addedItem.getPrice());
    }
}
