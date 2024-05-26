package id.ac.ui.cs.advprog.besell.service;

import id.ac.ui.cs.advprog.besell.model.Cart;
import id.ac.ui.cs.advprog.besell.model.Checkout;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CheckoutService {
    CompletableFuture<Checkout> processCheckout(List<Cart> cartItems, String shippingAddress, String paymentMethod);
}
