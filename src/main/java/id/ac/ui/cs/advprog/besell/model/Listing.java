package id.ac.ui.cs.advprog.besell.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@Entity
@Table(name = "listing")
public class Listing {
    @Id
    private String id;

    private String name;
    private int price;
    private int stock;
    private String description;
    private String imageUrl;
    private String sellerId;

    public Listing(){

    }
    public Listing(ListingBuilder builder){
        this.id = UUID.randomUUID().toString();
        this.name = builder.name;
        this.price = builder.price;
        this.stock = builder.stock;
        this.description = builder.description;
        this.imageUrl = builder.imageUrl;
        this.sellerId = builder.sellerId;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            this.stock = 0;
        } else {
            this.stock = stock;
        }
    }

    public static class ListingBuilder {
        // Required parameters
        private String name;
        private int price;

        // Optional parameters
        private int stock;
        private String description;
        private String imageUrl;
        private String sellerId;

        public ListingBuilder(String name, int price) {
            this.name = name;
            this.price = price;
        }

        public ListingBuilder setStock(int stock) {
            this.stock = stock;
            return this;
        }

        public ListingBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public ListingBuilder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public ListingBuilder setSellerId(String sellerId) {
            this.sellerId = sellerId;
            return this;
        }

        public Listing build() {
            return new Listing(this);
        }
    }
}
