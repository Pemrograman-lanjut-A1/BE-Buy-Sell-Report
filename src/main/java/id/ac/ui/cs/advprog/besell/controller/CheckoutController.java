package id.ac.ui.cs.advprog.besell.controller;

import id.ac.ui.cs.advprog.besell.model.Cart;
import id.ac.ui.cs.advprog.besell.model.Checkout;
import id.ac.ui.cs.advprog.besell.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private CartController cartController;

    @PostMapping
    public CompletableFuture<ResponseEntity<Checkout>> processCheckout(@RequestBody Checkout checkout) {
        List<Cart> cartItems = cartController.getCartItems().getBody();
        String shippingAddress = checkout.getShippingAddress();
        String paymentMethod = checkout.getPaymentMethod();

        return checkoutService.processCheckout(cartItems, shippingAddress, paymentMethod)
                .thenApply(ResponseEntity::ok);
    }
}