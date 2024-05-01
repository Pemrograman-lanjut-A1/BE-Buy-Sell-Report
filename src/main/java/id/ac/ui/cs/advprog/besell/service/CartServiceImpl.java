package id.ac.ui.cs.advprog.besell.service;

import id.ac.ui.cs.advprog.besell.model.Cart;
import id.ac.ui.cs.advprog.besell.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart addItemToCart(Cart cartItem) {
        return null;
    }

    @Override
    public List<Cart> getCartItems() {
        return cartRepository.getCartItems();
    }

    @Override
    public Cart updateItemQuantity() {
        return null;
    }

    @Override
    public Cart updateItemQuantity(String productId, int newQuantity) {
        return cartRepository.updateItemQuantity(productId, newQuantity);
    }

    @Override
    public Cart removeItemFromCart(String productId) {
        return cartRepository.removeItemFromCart(productId) ? new Cart.Builder(productId, "Product")
                .quantity(0)
                .price(0)
                .build() : null;
    }
}
