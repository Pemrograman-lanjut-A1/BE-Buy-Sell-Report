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
    }

    @Test
    void testFindAllIfEmpty() {
    }

    @Test
    void testFindAllIfMoreThanOneListing() {
    }

    @Test
    void testEditListing() {
    }

    @Test
    void testDeleteListing() {
    }
}
