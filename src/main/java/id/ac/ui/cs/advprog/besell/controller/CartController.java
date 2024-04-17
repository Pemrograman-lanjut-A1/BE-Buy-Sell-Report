package id.ac.ui.cs.advprog.besell.controller;

import id.ac.ui.cs.advprog.besell.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/cart", produces="application/json")
@CrossOrigin(origins="*")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/add-item")
    public String addItemToCart() {
        return "AddItemToCart";
    }

    @GetMapping("/get-items")
    public String getCartItems() {
        return "GetCartItems";
    }

    @PostMapping("/update-item-quantity")
    public String updateItemQuantity() {
        return "UpdateItemQuantity";
    }

    @DeleteMapping("/remove-item/{productId}")
    public String removeItemFromCart(@PathVariable String productId) {
        return "RemoveItemFromCart";
    }
}