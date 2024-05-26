package id.ac.ui.cs.advprog.besell.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderListingTest {
    OrderListing orderListing;

    @BeforeEach
    void SetUp(){
        this.orderListing = new OrderListing(
                "0c67e2cf-92b7-4918-851a-8a54b0b0f328",
                "ed6dd418-d7c1-4cb9-88fd-82e9d2d0b510",
                3
        );
    }
    @Test
    void testGetListingOrderId(){
        assertEquals("0c67e2cf-92b7-4918-851a-8a54b0b0f328", this.orderListing.getOrderId());
    }
    @Test
    void testGetListingListingId(){
        assertEquals("ed6dd418-d7c1-4cb9-88fd-82e9d2d0b510", this.orderListing.getListingId());
    }
    @Test
    void testGetListingQuantity(){
        assertEquals(3, this.orderListing.getQuantity());
    }
    @Test
    void testGetListingQuantityIfNegative(){
        this.orderListing = new OrderListing(
                "0c67e2cf-92b7-4918-851a-8a54b0b0f328",
                "ed6dd418-d7c1-4cb9-88fd-82e9d2d0b510",
                -3
        );
        assertEquals(0, this.orderListing.getQuantity());
    }
    @Test
    void testGetListingQuantitySetNegative(){
        this.orderListing.setQuantity(-3);
        assertEquals(0, this.orderListing.getQuantity());
    }
    @Test
    void testSetId(){
        RelationshipId pk = new RelationshipId("order", "listing");
        this.orderListing.setId(pk);
        assertEquals(pk, this.orderListing.getId());
        assertEquals("order", this.orderListing.getOrderId());
        assertEquals("listing", this.orderListing.getListingId());
    }
}
