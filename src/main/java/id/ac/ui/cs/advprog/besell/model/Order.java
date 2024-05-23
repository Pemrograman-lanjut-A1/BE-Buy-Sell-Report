package id.ac.ui.cs.advprog.besell.model;

import id.ac.ui.cs.advprog.besell.enums.OrderStatus;
import id.ac.ui.cs.advprog.besell.model.builder.OrderBuilder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter @Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name="status")
    private String status;

    @Column(name = "buyer_id", nullable = false)
    private String buyerId;

    public Order(){

    }
    public Order(OrderBuilder builder){
        this.id = UUID.randomUUID().toString();
        this.status = builder.getStatus();
        this.buyerId = builder.getBuyerId();
    }

    public void setStatus(String status) {
        if(OrderStatus.contains(status)){
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
