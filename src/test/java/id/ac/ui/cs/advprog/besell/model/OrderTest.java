package id.ac.ui.cs.advprog.besell.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
public class OrderTest {
    Order order;
    Order.OrderBuilder orderBuilder;
    Listing listing;

    @BeforeEach
    void SetUp(){
        Map<Listing, Integer> items = new HashMap<>();
        Listing.ListingBuilder listingBuilder = new Listing.ListingBuilder("Red Sweater", 12000);
        this.listing = listingBuilder.build();
        items.put(listing, 2);
        this.orderBuilder = new Order.OrderBuilder(items);
        this.order = orderBuilder.setBuyerId("FakeBuyerId")
                .build();
    }
    @Test
    void testGetOrderId(){
        assertNotNull(this.order.getId());
    }

    @Test
    void testGetOrderItems(){
        Map<Listing, Integer> items = this.order.getItems();
        assertFalse(items.isEmpty());
        assertTrue(items.containsKey(listing));
        assertTrue(items.containsValue(2));
    }

    @Test
    void testGetOrderStatusDefault(){
        assertEquals("WAITING_PAYMENT", this.order.getStatus());
    }

    @Test
    void testGetOrderStatusSuccess(){
        order.setStatus("SUCCESS");
        assertEquals("SUCCESS", this.order.getStatus());
    }

    @Test
    void testGetOrderBuyer(){
        assertEquals("FakeBuyerId", this.order.getBuyerId());
    }
}


