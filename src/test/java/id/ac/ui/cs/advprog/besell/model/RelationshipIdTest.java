package id.ac.ui.cs.advprog.besell.model;

import id.ac.ui.cs.advprog.besell.model.builder.ListingBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class RelationshipIdTest {
    RelationshipId relationshipId;
    ListingBuilder builder;

    @BeforeEach
    void SetUp(){
        this.relationshipId = new RelationshipId("order", "listing");
    }
    @Test
    void testGetListingId(){
        assertNotNull(this.relationshipId.getListingId());
    }

    @Test
    void testGetOrderId(){
        assertNotNull(this.relationshipId.getOrderId());
    }

    @Test
    void testSetOrderId(){
        this.relationshipId.setOrderId("order2");
        assertEquals("order2", this.relationshipId.getOrderId());
    }

    @Test
    void testSetListingId(){
        this.relationshipId.setListingId("listing2");
        assertEquals("listing2", this.relationshipId.getListingId());
    }
    @Test
    void testEqualsTrue(){
        RelationshipId other = new RelationshipId("order","listing");
        assertEquals(this.relationshipId, other);
    }
    @Test
    void testEqualsSame(){
        assertEquals(this.relationshipId, this.relationshipId);
    }
    @Test
    void testEqualsNull(){
        assertFalse(this.relationshipId.equals(null));
    }
}

