package id.ac.ui.cs.advprog.besell.service;

import id.ac.ui.cs.advprog.besell.model.Cart;
import id.ac.ui.cs.advprog.besell.model.Checkout;
import id.ac.ui.cs.advprog.besell.repository.CheckoutRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CheckoutServiceImplTest {

    @Mock
    private CheckoutRepository checkoutRepository;

    @Mock
    private CartService cartService;

    @InjectMocks
    private CheckoutServiceImpl checkoutService;

    private List<Cart> cartItems;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartItems = Arrays.asList(
                new Cart.Builder("1", "Product 1").quantity(2).price(10.0).build(),
                new Cart.Builder("2", "Product 2").quantity(1).price(20.0).build()
        );
    }

    @Test
    void testProcessCheckout() throws Exception {
        // Arrange
        String shippingAddress = "123 Main St.";
        String paymentMethod = "Credit Card";
        double totalAmount = 40.0;
        Checkout expectedCheckout = new Checkout(cartItems, totalAmount, shippingAddress, paymentMethod);

        when(cartService.getCartItems()).thenReturn(cartItems);
        when(checkoutRepository.processCheckout(any())).thenReturn(CompletableFuture.completedFuture(expectedCheckout));

        // Act
        CompletableFuture<Checkout> checkoutFuture = checkoutService.processCheckout(cartItems, shippingAddress, paymentMethod);
        Checkout actualCheckout = checkoutFuture.get();

        // Assert
        verify(cartService, times(1)).getCartItems();
        verify(checkoutRepository, times(1)).processCheckout(any());

        // Compare individual fields instead of objects
        assertEquals(expectedCheckout.getShippingAddress(), actualCheckout.getShippingAddress());
        assertEquals(expectedCheckout.getPaymentMethod(), actualCheckout.getPaymentMethod());
        assertEquals(expectedCheckout.getTotalAmount(), actualCheckout.getTotalAmount());
        assertEquals(expectedCheckout.getCartItems(), actualCheckout.getCartItems());
    }

    @Test
    void testClearCart() {
        List<Cart> cartItems = Arrays.asList(
                new Cart.Builder("1", "Product 1").quantity(2).price(10.0).build(),
                new Cart.Builder("2", "Product 2").quantity(1).price(20.0).build()
        );

        when(cartService.getCartItems()).thenReturn(cartItems);

        checkoutService.clearCart();

        verify(cartService, times(1)).removeItemFromCart("1");
        verify(cartService, times(1)).removeItemFromCart("2");
    }

    @Test
    void testCalculateTotalAmount() {
        List<Cart> cartItems = Arrays.asList(
                new Cart.Builder("1", "Product 1").quantity(2).price(10.0).build(),
                new Cart.Builder("2", "Product 2").quantity(1).price(20.0).build()
        );

        double expectedTotalAmount = 2 * 10.0 + 1 * 20.0;
        double actualTotalAmount = checkoutService.calculateTotalAmount(cartItems);
        assertEquals(expectedTotalAmount, actualTotalAmount);
    }
}