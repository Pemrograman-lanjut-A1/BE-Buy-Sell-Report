package id.ac.ui.cs.advprog.besell.model;

import id.ac.ui.cs.advprog.besell.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter @Setter
public class Order {
    private String id;

    private Map<Listing, Integer> items;
    private String status;
    private String buyerId;

    public Order(){

    }
    public Order(Order.OrderBuilder builder){
        this.id = UUID.randomUUID().toString();
        this.items = builder.items;
        this.status = builder.status;
        this.buyerId = builder.buyerId;
    }

    public void setStatus(String status) {
        if(OrderStatus.contains(status)){
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static class OrderBuilder {
        // Required parameters
        private Map<Listing, Integer> items;

        // Optional parameters
        private String status;
        private String buyerId;

        public OrderBuilder(Map<Listing, Integer> items) {
            this.items = items;
            this.status = OrderStatus.WAITING_PAYMENT.getValue();
        }

        public Order.OrderBuilder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Order.OrderBuilder setBuyerId(String buyerId) {
            this.buyerId = buyerId;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
