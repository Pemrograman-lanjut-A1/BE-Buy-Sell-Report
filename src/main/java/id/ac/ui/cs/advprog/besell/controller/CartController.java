package id.ac.ui.cs.advprog.besell.controller;

import id.ac.ui.cs.advprog.besell.model.Cart;
import id.ac.ui.cs.advprog.besell.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/cart", produces = "application/json")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/items")
    public ResponseEntity<String> addItemToCart(@RequestBody Cart cartItem) {
        cartService.addItemToCart(cartItem);
        return ResponseEntity.status(HttpStatus.CREATED).body("Item added to cart");
    }

    @GetMapping("/items")
    public ResponseEntity<List<Cart>> getCartItems() {
        List<Cart> cartItems = cartService.getCartItems();
        return ResponseEntity.ok(cartItems);
    }

    @PutMapping("/items/{productId}")
    public ResponseEntity<String> updateItemQuantity(@PathVariable String productId, @RequestParam int quantity) {
        cartService.updateItemQuantity(productId, quantity);
        return ResponseEntity.ok("Item quantity updated");
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<String> removeItemFromCart(@PathVariable String productId) {
        cartService.removeItemFromCart(productId);
        return ResponseEntity.ok("Item removed from cart");
    }
}
