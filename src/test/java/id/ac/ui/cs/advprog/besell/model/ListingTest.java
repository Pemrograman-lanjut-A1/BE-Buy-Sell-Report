package id.ac.ui.cs.advprog.besell.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ListingTest {
    Listing listing;

    @BeforeEach
    void SetUp(){
        this.listing = new Listing();
        this.listing.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.listing.setListingName("Red Sweater");
        this.listing.setListingPrice(12000);
        this.listing.setListingStock(100);
    }
    @Test
    void testGetListingId(){
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.listing.getListingId());
    }

    @Test
    void testGetListingName(){
        assertEquals("Red Sweater", this.listing.getListingName());
    }

    @Test
    void testGetListingPrice(){
        assertEquals(12000, this.listing.getListingPrice());
    }

    @Test
    void testGetListingQuantity(){
        assertEquals(100, this.listing.getListingStock());
    }
}

