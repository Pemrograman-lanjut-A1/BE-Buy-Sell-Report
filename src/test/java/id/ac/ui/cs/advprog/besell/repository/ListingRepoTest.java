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
        Listing listing = new Listing();
        listing.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        listing.setListingName("Sampo Cap Bambang");
        listing.setListingQuantity(100);
        listing.setListingPrice(1200);
        listingRepository.create(listing);

        Iterator<Listing> listingIterator = listingRepository.findAll();
        assertTrue(listingIterator.hasNext());
        Listing savedListing = listingIterator.next();
        assertEquals(listing.getListingId(), savedListing.getListingId());
        assertEquals(listing.getListingName(), savedListing.getListingName());
        assertEquals(listing.getListingQuantity(), savedListing.getListingQuantity());
        assertEquals(listing.getListingPrice(), savedListing.getListingPrice());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Listing> listingIterator = listingRepository.findAll();
        assertFalse(listingIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneListing() {
        Listing listing1 = new Listing();
        listing1.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        listing1.setListingName("Sampo Cap Bambang");
        listing1.setListingQuantity(100);
        listingRepository.create(listing1);

        Listing listing2 = new Listing();
        listing2.setListingId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        listing2.setListingName("Sampo Cap Usep");
        listing2.setListingQuantity(50);
        listingRepository.create(listing2);

        Iterator<Listing> listingIterator = listingRepository.findAll();
        assertTrue(listingIterator.hasNext());
        Listing savedListing = listingIterator.next();
        assertEquals(listing1.getListingId(), savedListing.getListingId());
        savedListing = listingIterator.next();
        assertEquals(listing2.getListingId(), savedListing.getListingId());
        assertFalse(listingIterator.hasNext());
    }

    @Test
    void testEditListing() {
        Listing listing = new Listing();
        listing.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        listing.setListingName("Sampo Cap Bambang");
        listing.setListingQuantity(100);
        listingRepository.create(listing);

        Listing editedListing = new Listing();
        editedListing.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        editedListing.setListingName("Sampo Cap Bango");
        editedListing.setListingQuantity(-5);
        listingRepository.update(editedListing);

        listing = listingRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals(editedListing.getListingId(), listing.getListingId());
        assertEquals(editedListing.getListingName(), listing.getListingName());
        assertEquals(editedListing.getListingQuantity(), listing.getListingQuantity());
    }

    @Test
    void testDeleteListing() {
        Listing listing1 = new Listing();
        listing1.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        listing1.setListingName("Sampo Cap Bambang");
        listing1.setListingQuantity(100);
        listingRepository.create(listing1);

        listingRepository.delete(listing1);

        assertFalse(listingRepository.findAll().hasNext());
    }
}
