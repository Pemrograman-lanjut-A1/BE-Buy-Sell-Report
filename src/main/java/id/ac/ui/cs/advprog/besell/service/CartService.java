package id.ac.ui.cs.advprog.besell.service;

import id.ac.ui.cs.advprog.besell.model.Cart;
import java.util.List;

public interface CartService {
    Cart addItemToCart(Cart cartItem);
    List<Cart> getCartItems();
    Cart updateItemQuantity();
    Cart updateItemQuantity(String productId, int newQuantity);
    Cart removeItemFromCart(String productId);
}


