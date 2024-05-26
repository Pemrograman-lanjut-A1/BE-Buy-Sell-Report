package id.ac.ui.cs.advprog.besell.model;

import id.ac.ui.cs.advprog.besell.model.builder.ListingBuilder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@Entity
@Table(name = "listing")
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "stock")
    private int stock;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "seller_id")
    private String sellerId;

    public Listing(){

    }
    public Listing(ListingBuilder builder){
        this.id = UUID.randomUUID().toString();
        this.name = builder.getName();
        this.price = builder.getPrice();
        this.stock = builder.getStock();
        this.description = builder.getDescription();
        this.imageUrl = builder.getImageUrl();
        this.sellerId = builder.getSellerId();
    }

    public void setStock(int stock) {
        if (stock < 0) {
            this.stock = 0;
        } else {
            this.stock = stock;
        }
    }
}
