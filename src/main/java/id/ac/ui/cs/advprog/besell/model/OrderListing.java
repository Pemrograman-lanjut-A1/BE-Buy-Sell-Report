package id.ac.ui.cs.advprog.besell.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@Entity
@IdClass(RelationshipId.class)
@Table(name = "order_listing")
public class OrderListing {
    @Id
    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Id
    @Column(name = "listing_id", nullable = false)
    private String listingId;

    @Column(name = "quantity")
    private int quantity;

    public RelationshipId getId() {
        return new RelationshipId(
                orderId,
                listingId
        );
    }

    public void setId(RelationshipId id) {
        this.orderId = id.getOrderId();
        this.listingId = id.getListingId();
    }

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
