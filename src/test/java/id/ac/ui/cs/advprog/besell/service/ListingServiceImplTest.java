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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
    void testCreateAndFind() throws ExecutionException, InterruptedException{
        Listing.ListingBuilder builder = new Listing.ListingBuilder("Red Sweater", 12000);
        Listing listing = builder.setStock(99)
                .setDescription("The color of the sweater is red")
                .setImageUrl("google.com")
                .build();

        Mockito.when(listingRepository.save(listing)).thenReturn(listing);
        listingService.create(listing);

        Mockito.when(listingRepository.findAll()).thenReturn(List.of(listing));
        CompletableFuture<List<Listing>> listingListFuture = listingService.findAll();
        List<Listing> listingList = listingListFuture.get();

        assertFalse(listingList.isEmpty());
        Listing savedListing = listingList.getFirst();
        assertEquals(listing.getId(), savedListing.getId());
        assertEquals(listing.getName(), savedListing.getName());
        assertEquals(listing.getStock(), savedListing.getStock());
    }

    @Test
    void testFindAllIfEmpty() throws ExecutionException, InterruptedException {
        List<Listing> listingList = new ArrayList<>();
        Mockito.when(listingRepository.findAll()).thenReturn(listingList);

        CompletableFuture<List<Listing>> listingsFuture = listingService.findAll();
        List<Listing> listings = listingsFuture.get();

        assertTrue(listings.isEmpty());
    }

    @Test
    void testFindAllIfMoreThanOneListing() throws ExecutionException, InterruptedException {
        Listing.ListingBuilder builder = new Listing.ListingBuilder("Red Sweater", 12000);
        Listing listing1 = builder.setStock(99)
                .setDescription("The color of the sweater is red")
                .setImageUrl("google.com")
                .build();

        Mockito.when(listingRepository.save(listing1)).thenReturn(listing1);
        listingService.create(listing1);

        Listing listing2 = builder.setStock(99)
                .setDescription("The color of the jeans is blue")
                .setImageUrl("bluejeans.com")
                .build();
        Mockito.when(listingRepository.save(listing2)).thenReturn(listing2);
        listingService.create(listing2);

        Mockito.when(listingRepository.findAll()).thenReturn(List.of(listing1, listing2));
        CompletableFuture<List<Listing>> listingListFuture = listingService.findAll();
        List<Listing> listingList = listingListFuture.get();

        assertFalse(listingList.isEmpty());
        assertEquals(listing1.getId(), listingList.getFirst().getId());
        assertEquals(listing2.getId(), listingList.get(1).getId());
        assertFalse(listingList.isEmpty());
    }

    @Test
    void testEditListing() throws ExecutionException, InterruptedException {
        Listing.ListingBuilder builder = new Listing.ListingBuilder("Red Sweater", 12000);
        Listing listing = builder.setStock(99)
                .setDescription("The color of the sweater is red")
                .setImageUrl("google.com")
                .build();
        listing.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        Mockito.when(listingRepository.save(listing)).thenReturn(listing);
        listingService.create(listing);

        builder = new Listing.ListingBuilder("Blue Jeans", 12000);
        Listing editedListing = builder.setStock(99)
                .setDescription("The color of the sweater is red")
                .setImageUrl("google.com")
                .build();
        editedListing.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        listingService.update(editedListing);

        Mockito.when(listingRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(Optional.of(editedListing));
        CompletableFuture<Optional<Listing>> resultListingFuture = listingService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        Optional<Listing> resultListing = resultListingFuture.get();

        assertEquals(editedListing, resultListing.get());
        Mockito.verify(listingRepository).save(editedListing);
    }

    @Test
    void testDeleteListing() {
        Listing.ListingBuilder builder = new Listing.ListingBuilder("Red Sweater", 12000);
        Listing listing1 = builder.setStock(99)
                .setDescription("The color of the sweater is red")
                .setImageUrl("google.com")
                .build();

        Mockito.when(listingRepository.save(listing1)).thenReturn(listing1);
        listingService.create(listing1);

        listingService.delete(listing1.getId());

        Mockito.verify(listingRepository).deleteById(listing1.getId());
    }
}
