package id.ac.ui.cs.advprog.besell.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Checkout {
    private List<Cart> cartItems;
    private double totalAmount;
    private String shippingAddress;
    private String paymentMethod;
}