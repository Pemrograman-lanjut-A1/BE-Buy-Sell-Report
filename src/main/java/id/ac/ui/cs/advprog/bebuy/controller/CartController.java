package id.ac.ui.cs.advprog.bebuy.controller;

import id.ac.ui.cs.advprog.bebuy.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path="/cart", produces="application/json")
@CrossOrigin(origins="*")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add-item")
    public void addItemToCart(@RequestBody CartItem cartItem) {
    }

    @GetMapping("/get-items")
    public List<CartItem> getCartItems() {
    }

    @PostMapping("/update-item-quantity")
    public void updateItemQuantity(@RequestParam long productId, @RequestParam int quantity) {
    }

    @DeleteMapping("/remove-item/{productId}")
    public void removeItemFromCart(@PathVariable long productId) {
    }
}