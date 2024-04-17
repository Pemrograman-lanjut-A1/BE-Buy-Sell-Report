package id.ac.ui.cs.advprog.bebuy.service;

import id.ac.ui.cs.advprog.bebuy.model.Cart;
import id.ac.ui.cs.advprog.bebuy.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Override
    public Cart addItemToCart() {
        return null;
    }

    @Override
    public List<Cart> getCartItems() {
        return null;
    }

    @Override
    public Cart updateItemQuantity() {
        return null;
    }

    @Override
    public Cart removeItemFromCart() {
        return null;
    }
}