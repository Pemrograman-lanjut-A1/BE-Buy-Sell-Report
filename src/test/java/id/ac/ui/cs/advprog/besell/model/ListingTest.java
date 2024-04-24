package id.ac.ui.cs.advprog.besell.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ListingTest {
    Listing listing;
    Listing.ListingBuilder builder;

    @BeforeEach
    void SetUp(){
        this.builder = new Listing.ListingBuilder("Red Sweater", 12000);
        this.listing = builder.setStock(99)
                .setDescription("The color of the sweater is red")
                .setImageUrl("google.com")
                .build();
    }
    @Test
    void testGetListingId(){
        assertNotNull(this.listing.getId());
    }

    @Test
    void testGetListingName(){
        assertEquals("Red Sweater", this.listing.getName());
    }

    @Test
    void testGetListingDescription(){
        assertEquals("The color of the sweater is red", this.listing.getDescription());
    }

    @Test
    void testGetListingImageUrl(){
        assertEquals("google.com", this.listing.getImageUrl());
    }

    @Test
    void testGetListingPrice(){
        assertEquals(12000, this.listing.getPrice());
    }

    @Test
    void testGetListingStock(){
        assertEquals(99, this.listing.getStock());
    }

    @Test
    void testGetListingStockIfNegative(){
        this.listing.setStock(-1);
        assertEquals(0, this.listing.getStock());
    }

}

