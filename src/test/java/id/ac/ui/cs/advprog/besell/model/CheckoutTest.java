package id.ac.ui.cs.advprog.besell.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CheckoutTest {

    @Test
    void testConstructor() {
        List<Cart> cartItems = Arrays.asList(
                new Cart.Builder("1", "Product 1").quantity(2).price(10.0).build(),
                new Cart.Builder("2", "Product 2").quantity(1).price(20.0).build()
        );
        double totalAmount = 40.0;
        String shippingAddress = "123 Main St.";
        String paymentMethod = "Credit Card";

        Checkout checkout = new Checkout(cartItems, totalAmount, shippingAddress, paymentMethod);

        assertEquals(cartItems, checkout.getCartItems());
        assertEquals(totalAmount, checkout.getTotalAmount());
        assertEquals(shippingAddress, checkout.getShippingAddress());
        assertEquals(paymentMethod, checkout.getPaymentMethod());
    }
}
