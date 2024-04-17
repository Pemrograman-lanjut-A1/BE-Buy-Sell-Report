package id.ac.ui.cs.advprog.besell.service;

import id.ac.ui.cs.advprog.besell.model.Cart;
import java.util.List;

public interface CartService {
    Cart addItemToCart();
    List<Cart> getCartItems();
    Cart updateItemQuantity();
    Cart removeItemFromCart();
}


