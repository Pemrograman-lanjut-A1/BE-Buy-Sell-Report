package id.ac.ui.cs.advprog.besell.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.besell.model.Cart;
import id.ac.ui.cs.advprog.besell.model.Checkout;
import id.ac.ui.cs.advprog.besell.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping
    public CompletableFuture<ResponseEntity<Checkout>> processCheckout(@RequestBody Checkout checkout, @RequestHeader(value = "Authorization") String token) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String cartItemsResponse = cartController.getCartItems(token).join();
                ResponseEntity<String> responseEntity = objectMapper.readValue(cartItemsResponse, ResponseEntity.class);

                if (responseEntity.getStatusCodeValue() != HttpStatus.OK.value()) {
                    return ResponseEntity.status(responseEntity.getStatusCodeValue()).build();
                }

                List<Cart> cartItems = objectMapper.readValue(responseEntity.getBody(), objectMapper.getTypeFactory().constructCollectionType(List.class, Cart.class));

                String shippingAddress = checkout.getShippingAddress();
                String paymentMethod = checkout.getPaymentMethod();

                return checkoutService.processCheckout(cartItems, shippingAddress, paymentMethod)
                        .thenApply(ResponseEntity::ok)
                        .join();
            } catch (JsonProcessingException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        });
    }
}
