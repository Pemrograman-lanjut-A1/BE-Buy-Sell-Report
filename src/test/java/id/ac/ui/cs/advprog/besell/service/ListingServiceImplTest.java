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
        Listing.ListingBuilder builder = new Listing.ListingBuilder("Red Sweater", 12000);
        Listing listing = builder.setStock(99)
                .setDescription("The color of the sweater is red")
                .setImageUrl("google.com")
                .build();

        Mockito.when(listingRepository.create(listing)).thenReturn(listing);
        listingService.create(listing);

        Mockito.when(listingRepository.findAll()).thenReturn(List.of(listing).iterator());
        List<Listing> listingList = listingService.findAll();

        assertFalse(listingList.isEmpty());
        Listing savedListing = listingList.getFirst();
        assertEquals(listing.getId(), savedListing.getId());
        assertEquals(listing.getName(), savedListing.getName());
        assertEquals(listing.getStock(), savedListing.getStock());
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
        Listing.ListingBuilder builder = new Listing.ListingBuilder("Red Sweater", 12000);
        Listing listing1 = builder.setStock(99)
                .setDescription("The color of the sweater is red")
                .setImageUrl("google.com")
                .build();

        Mockito.when(listingRepository.create(listing1)).thenReturn(listing1);
        listingService.create(listing1);

        Listing listing2 = builder.setStock(99)
                .setDescription("The color of the jeans is blue")
                .setImageUrl("bluejeans.com")
                .build();
        Mockito.when(listingRepository.create(listing2)).thenReturn(listing2);
        listingService.create(listing2);

        Mockito.when(listingRepository.findAll()).thenReturn(List.of(listing1, listing2).iterator());
        List<Listing> listingList = listingService.findAll();

        assertFalse(listingList.isEmpty());
        Listing savedListing = listingList.removeFirst();
        assertEquals(listing1.getId(), savedListing.getId());
        savedListing = listingList.removeFirst();
        assertEquals(listing2.getId(), savedListing.getId());
        assertTrue(listingList.isEmpty());
    }

    @Test
    void testEditListing() {
        Listing.ListingBuilder builder = new Listing.ListingBuilder("Red Sweater", 12000);
        Listing listing = builder.setStock(99)
                .setDescription("The color of the sweater is red")
                .setImageUrl("google.com")
                .build();

        Mockito.when(listingRepository.create(listing)).thenReturn(listing);
        listingService.create(listing);

        Listing editedListing = listing;
        editedListing.setName("Blue Jeans");
        listingService.update(editedListing);

        Mockito.when(listingRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(editedListing);
        Listing resultListing = listingService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");

        assertEquals(editedListing, resultListing);
        Mockito.verify(listingRepository).update(editedListing);
    }

    @Test
    void testDeleteListing() {
        Listing.ListingBuilder builder = new Listing.ListingBuilder("Red Sweater", 12000);
        Listing listing1 = builder.setStock(99)
                .setDescription("The color of the sweater is red")
                .setImageUrl("google.com")
                .build();

        Mockito.when(listingRepository.create(listing1)).thenReturn(listing1);
        listingService.create(listing1);

        listingService.delete(listing1);

        Mockito.verify(listingRepository).delete(listing1);
    }
}
