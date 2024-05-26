package id.ac.ui.cs.advprog.besell.controller;

import id.ac.ui.cs.advprog.besell.model.Cart;
import id.ac.ui.cs.advprog.besell.model.Checkout;
import id.ac.ui.cs.advprog.besell.service.CheckoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CheckoutControllerTest {

    @Mock
    private CheckoutService checkoutService;

    @Mock
    private CartController cartController;

    @InjectMocks
    private CheckoutController checkoutController;

    private List<Cart> cartItems;
    private Checkout checkout;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartItems = Arrays.asList(
                new Cart.Builder("1", "Product 1").quantity(2).price(10.0).build(),
                new Cart.Builder("2", "Product 2").quantity(1).price(20.0).build()
        );
        double totalAmount = 40.0;
        String shippingAddress = "123 Main St.";
        String paymentMethod = "Credit Card";
        checkout = new Checkout(cartItems, totalAmount, shippingAddress, paymentMethod);
    }

    @Test
    void testProcessCheckout() {
        when(cartController.getCartItems()).thenReturn(ResponseEntity.ok(cartItems));
        when(checkoutService.processCheckout(cartItems, checkout.getShippingAddress(), checkout.getPaymentMethod()))
                .thenReturn(CompletableFuture.completedFuture(checkout));

        CompletableFuture<ResponseEntity<Checkout>> checkoutResponse = checkoutController.processCheckout(checkout);
        ResponseEntity<Checkout> expectedResponse = ResponseEntity.ok(checkout);

        assertEquals(expectedResponse, checkoutResponse.join());
    }
}
