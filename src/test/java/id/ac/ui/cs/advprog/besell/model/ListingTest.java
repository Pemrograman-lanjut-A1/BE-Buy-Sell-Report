package id.ac.ui.cs.advprog.besell.model;

import id.ac.ui.cs.advprog.besell.model.builder.ListingBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ListingTest {
    Listing listing;
    ListingBuilder builder;

    @BeforeEach
    void SetUp(){
        this.builder = new ListingBuilder("Red Sweater", 12000);
        this.listing = builder.setStock(99)
                .setDescription("The color of the sweater is red")
                .setImageUrl("google.com")
                .setSellerId("FakeSellerId")
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
    void testGetListingSeller(){
        assertEquals("FakeSellerId", this.listing.getSellerId());
    }

    @Test
    void testGetListingStockIfNegative(){
        this.listing.setStock(-1);
        assertEquals(0, this.listing.getStock());
    }

}

