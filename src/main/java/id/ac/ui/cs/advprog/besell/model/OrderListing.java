package id.ac.ui.cs.advprog.besell.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@Entity
@Table(name = "order_listing")
public class OrderListing {
    @Id
    private String orderId;
    @Id
    private String listingId;
    private int quantity;

    public OrderListing(){

    }

    public OrderListing(String orderId, String listingId, int quantity){
        this.orderId = orderId;
        this.listingId = listingId;
        this.quantity = Math.max(quantity, 0);
    }

    public void setQuantity(int qty){
        this.quantity = Math.max(qty, 0);
    }
}
