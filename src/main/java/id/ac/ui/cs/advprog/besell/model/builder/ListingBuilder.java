package id.ac.ui.cs.advprog.besell.model.builder;

import id.ac.ui.cs.advprog.besell.model.Listing;
import lombok.Getter;

@Getter
public class ListingBuilder {
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