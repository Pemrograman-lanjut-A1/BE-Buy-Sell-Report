package id.ac.ui.cs.advprog.besell.repository;

import id.ac.ui.cs.advprog.besell.model.Cart;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Getter
@Repository
public class CartRepository {
    private List<Cart> cartItems = new ArrayList<>();

    public Cart addItemToCart(Cart cartItem) {
        cartItems.add(cartItem);
        return cartItem;
    }

    public Cart updateItemQuantity(String productId, int newQuantity) {
        for (Cart cartItem : cartItems) {
            if (cartItem.getProductId().equals(productId)) {
                cartItem.setQuantity(newQuantity);
                return cartItem;
            }
        }
        return null;
    }

    public boolean removeItemFromCart(String productId) {
        return cartItems.removeIf(cartItem -> cartItem.getProductId().equals(productId));
    }
}
