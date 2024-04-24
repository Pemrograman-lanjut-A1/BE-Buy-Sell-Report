package id.ac.ui.cs.advprog.besell.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class Listing {
    private String id;
    private String name;
    private int price;
    private int stock;
    private String description;
    private String imageUrl;

    public Listing(ListingBuilder builder){

        this.id = UUID.randomUUID().toString();
        this.name = builder.name;
        this.price = builder.price;
        this.stock = builder.stock;
        this.description = builder.description;
        this.imageUrl = builder.imageUrl;
    }

    public static class ListingBuilder {
        // Required parameters
        private String name;
        private int price;

        // Optional parameters
        private int stock;
        private String description;
        private String imageUrl;

        public ListingBuilder(String name, int price) {
            this.name = name;
            this.price = price;
        }

        public Listing build() {
            return new Listing(this);
        }
    }
}
