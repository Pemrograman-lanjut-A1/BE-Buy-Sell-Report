package id.ac.ui.cs.advprog.besell.service;

import id.ac.ui.cs.advprog.besell.model.Cart;
import id.ac.ui.cs.advprog.besell.model.Checkout;
import id.ac.ui.cs.advprog.besell.repository.CheckoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    private CheckoutRepository checkoutRepository;

    @Autowired
    private CartService cartService;

    @Override
    public CompletableFuture<Checkout> processCheckout(List<Cart> cartItems, String shippingAddress, String paymentMethod) {
        double totalAmount = calculateTotalAmount(cartItems);
        Checkout checkout = new Checkout(cartItems, totalAmount, shippingAddress, paymentMethod);
        return CompletableFuture.supplyAsync(() -> {
            checkoutRepository.processCheckout(checkout);
            clearCart();
            return checkout;
        });
    }

    double calculateTotalAmount(List<Cart> cartItems) {
        return cartItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    void clearCart() {
        cartService.getCartItems().forEach(item -> cartService.removeItemFromCart(item.getProductId()));
    }
}
