package id.ac.ui.cs.advprog.bebuy.service;

import id.ac.ui.cs.advprog.bebuy.model.Cart;
import java.util.List;

public interface CartService {
    public Cart addItemToCart();
    public List<CartItem> getCartItems();
    public Cart updateItemQuantity();
    public Cart removeItemFromCart();
}


