package id.ac.ui.cs.advprog.bebuy.service;

import id.ac.ui.cs.advprog.bebuy.model.Cart;
import java.util.List;

public interface CartService {
    Cart addItemToCart();
    List<Cart> getCartItems();
    Cart updateItemQuantity();
    Cart removeItemFromCart();
}


