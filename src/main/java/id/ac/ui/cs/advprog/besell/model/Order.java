package id.ac.ui.cs.advprog.besell.model;

import id.ac.ui.cs.advprog.besell.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter @Setter
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String itemId;
    private String status;
    private String buyerId;

    public Order(){

    }
    public Order(Order.OrderBuilder builder){
        this.id = UUID.randomUUID().toString();
        this.itemId = builder.itemId;
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
        private String itemId;

        // Optional parameters
        private String status;
        private String buyerId;

        public OrderBuilder(String itemId) {
            this.itemId = itemId;
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
