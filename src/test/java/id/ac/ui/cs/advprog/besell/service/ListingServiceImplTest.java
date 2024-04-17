package id.ac.ui.cs.advprog.besell.service;
import id.ac.ui.cs.advprog.besell.model.Listing;
import id.ac.ui.cs.advprog.besell.repository.ListingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ListingServiceImplTest {
    @InjectMocks
    ListingServiceImpl listingService;

    @Mock
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

        Mockito.when(listingRepository.create(listing)).thenReturn(listing);
        listingService.create(listing);

        Mockito.when(listingRepository.findAll()).thenReturn(List.of(listing).iterator());
        List<Listing> listingList = listingService.findAll();

        assertFalse(listingList.isEmpty());
        Listing savedListing = listingList.getFirst();
        assertEquals(listing.getListingId(), savedListing.getListingId());
        assertEquals(listing.getListingName(), savedListing.getListingName());
        assertEquals(listing.getListingQuantity(), savedListing.getListingQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        List<Listing> listingList = new ArrayList<>();
        Mockito.when(listingRepository.findAll()).thenReturn(listingList.iterator());

        List<Listing> listings = listingService.findAll();

        assertTrue(listings.isEmpty());
    }

    @Test
    void testFindAllIfMoreThanOneListing() {
        Listing listing1 = new Listing();
        listing1.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        listing1.setListingName("Sampo Cap Bambang");
        listing1.setListingQuantity(100);

        Mockito.when(listingRepository.create(listing1)).thenReturn(listing1);
        listingService.create(listing1);

        Listing listing2 = new Listing();
        listing2.setListingId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        listing2.setListingName("Sampo Cap Usep");
        listing2.setListingQuantity(50);
        Mockito.when(listingRepository.create(listing2)).thenReturn(listing2);
        listingService.create(listing2);

        Mockito.when(listingRepository.findAll()).thenReturn(List.of(listing1, listing2).iterator());
        List<Listing> listingList = listingService.findAll();

        assertFalse(listingList.isEmpty());
        Listing savedListing = listingList.removeFirst();
        assertEquals(listing1.getListingId(), savedListing.getListingId());
        savedListing = listingList.removeFirst();
        assertEquals(listing2.getListingId(), savedListing.getListingId());
        assertTrue(listingList.isEmpty());
    }

    @Test
    void testEditListing() {
        Listing listing = new Listing();
        listing.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        listing.setListingName("Sampo Cap Bambang");
        listing.setListingQuantity(100);

        Mockito.when(listingRepository.create(listing)).thenReturn(listing);
        listingService.create(listing);

        Listing editedListing = new Listing();
        editedListing.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        editedListing.setListingName("Sampo Cap Bango");
        editedListing.setListingQuantity(0);
        listingService.update(editedListing);

        Mockito.when(listingRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(editedListing);
        Listing resultListing = listingService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");

        assertEquals(editedListing, resultListing);
        Mockito.verify(listingRepository).update(editedListing);
    }

    @Test
    void testDeleteListing() {
        Listing listing1 = new Listing();
        listing1.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        listing1.setListingName("Sampo Cap Bambang");
        listing1.setListingQuantity(100);

        Mockito.when(listingRepository.create(listing1)).thenReturn(listing1);
        listingService.create(listing1);

        listingService.delete(listing1);

        Mockito.verify(listingRepository).delete(listing1);
    }
}
