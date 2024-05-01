package id.ac.ui.cs.advprog.besell.model;

import id.ac.ui.cs.advprog.besell.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class OrderTest {
    Order order;
    Order.OrderBuilder orderBuilder;
    Listing listing;

    @BeforeEach
    void SetUp(){
        Listing.ListingBuilder listingBuilder = new Listing.ListingBuilder("Red Sweater", 12000);
        this.listing = listingBuilder.build();
        this.orderBuilder = new Order.OrderBuilder(listing);
        this.order = orderBuilder.setBuyerId("FakeBuyerId")
                .build();
    }
    @Test
    void testGetOrderId(){
        assertNotNull(this.order.getId());
    }

    @Test
    void testGetOrderItem(){
        assertEquals(listing, this.order.getItem());
    }

    @Test
    void testGetOrderStatusDefault(){
        assertEquals(OrderStatus.WAITING_PAYMENT.getValue(), this.order.getStatus());
    }

    @Test
    void testGetOrderStatusSuccess(){
        order.setStatus("SUCCESS");
        assertEquals(OrderStatus.SUCCESS.getValue(), this.order.getStatus());
    }

    @Test
    void testGetOrderBuyer(){
        assertEquals("FakeBuyerId", this.order.getBuyerId());
    }
}


