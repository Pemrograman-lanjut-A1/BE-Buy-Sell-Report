package id.ac.ui.cs.advprog.besell.repository;

import id.ac.ui.cs.advprog.besell.model.Listing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ListingRepoTest {
    @InjectMocks
    ListingRepository listingRepository;

    @BeforeEach
    void SetUp(){
    }

    @Test
    void testCreateAndFind() {
        Listing.ListingBuilder builder = new Listing.ListingBuilder("Red Sweater", 12000);
        Listing listing = builder.setStock(99)
                .setDescription("The color of the sweater is red")
                .setImageUrl("google.com")
                .build();
        listingRepository.create(listing);

        Iterator<Listing> listingIterator = listingRepository.findAll();
        assertTrue(listingIterator.hasNext());
        Listing savedListing = listingIterator.next();
        assertEquals(listing.getId(), savedListing.getId());
        assertEquals(listing.getName(), savedListing.getName());
        assertEquals(listing.getStock(), savedListing.getStock());
        assertEquals(listing.getPrice(), savedListing.getPrice());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Listing> listingIterator = listingRepository.findAll();
        assertFalse(listingIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneListing() {
        Listing.ListingBuilder builder = new Listing.ListingBuilder("Red Sweater", 12000);
        Listing listing1 = builder.setStock(99)
                .setDescription("The color of the sweater is red")
                .setImageUrl("google.com")
                .build();
        listingRepository.create(listing1);

        builder = new Listing.ListingBuilder("Blue Jeans", 199000);
        Listing listing2 = builder.setStock(99)
                .setDescription("The color of the jeans is blue")
                .setImageUrl("bluejeans.com")
                .build();
        listingRepository.create(listing2);

        Iterator<Listing> listingIterator = listingRepository.findAll();
        assertTrue(listingIterator.hasNext());
        Listing savedListing = listingIterator.next();
        assertEquals(listing1.getId(), savedListing.getId());
        savedListing = listingIterator.next();
        assertEquals(listing2.getId(), savedListing.getId());
        assertFalse(listingIterator.hasNext());
    }

    @Test
    void testEditListing() {
        Listing.ListingBuilder builder = new Listing.ListingBuilder("Red Sweater", 12000);
        Listing listing = builder.setStock(99)
                .setDescription("The color of the sweater is red")
                .setImageUrl("google.com")
                .build();
        listing.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        listingRepository.create(listing);

        Listing editedListing = listing;
        editedListing.setName("Blue Jeans");
        listingRepository.update(editedListing);

        listing = listingRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals(editedListing.getId(), listing.getId());
        assertEquals(editedListing.getName(), listing.getName());
        assertEquals(editedListing.getStock(), listing.getStock());
    }

    @Test
    void testDeleteListing() {
        Listing.ListingBuilder builder = new Listing.ListingBuilder("Red Sweater", 12000);
        Listing listing = builder.setStock(99)
                .setDescription("The color of the sweater is red")
                .setImageUrl("google.com")
                .build();
        listingRepository.create(listing);

        listingRepository.delete(listing);

        assertFalse(listingRepository.findAll().hasNext());
    }
}
