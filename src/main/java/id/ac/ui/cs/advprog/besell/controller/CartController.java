package id.ac.ui.cs.advprog.besell.controller;

import id.ac.ui.cs.advprog.besell.model.Cart;
import id.ac.ui.cs.advprog.besell.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/cart", produces = "application/json")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add-item")
    public ResponseEntity<String> addItemToCart(@RequestBody Cart cartItem) {
        return ResponseEntity.ok("Item added to cart");
    }

    @GetMapping("/get-items")
    public ResponseEntity<List<Cart>> getCartItems() {
        List<Cart> cartItems = cartService.getCartItems();
        return ResponseEntity.ok(cartItems);
    }

    @PostMapping("/update-item-quantity")
    public ResponseEntity<String> updateItemQuantity(@RequestParam String productId, @RequestParam int quantity) {
        return ResponseEntity.ok("Item quantity updated");
    }

    @DeleteMapping("/remove-item/{productId}")
    public ResponseEntity<String> removeItemFromCart(@PathVariable String productId) {
        return ResponseEntity.ok("Item removed from cart");
    }
}
