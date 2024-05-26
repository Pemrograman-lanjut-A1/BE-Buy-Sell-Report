package id.ac.ui.cs.advprog.besell.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Cart {
    private final String productId;
    private final String productName;

    @Setter
    private int quantity;
    private final double price;

    public Cart(Builder builder) {
        this.productId = builder.productId;
        this.productName = builder.productName;
        this.quantity = builder.quantity;
        this.price = builder.price;
    }

    public static class Builder {
        private final String productId;
        private final String productName;
        private int quantity;
        private double price;

        public Builder(String productId, String productName) {
            this.productId = productId;
            this.productName = productName;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Cart build() {
            return new Cart(this);
        }
    }
}
